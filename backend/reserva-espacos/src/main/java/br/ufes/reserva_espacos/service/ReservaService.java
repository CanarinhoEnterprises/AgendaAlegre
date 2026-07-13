package br.ufes.reserva_espacos.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.reservadto.CadastroReservaDTO;
import br.ufes.reserva_espacos.dto.reservadto.CancelarReservaDTO;
import br.ufes.reserva_espacos.dto.reservadto.ReservaResponseDTO;
import br.ufes.reserva_espacos.entity.Espaco;
import br.ufes.reserva_espacos.entity.Reserva;
import br.ufes.reserva_espacos.entity.Solicitante;
import br.ufes.reserva_espacos.enums.StatusEspaco;
import br.ufes.reserva_espacos.enums.StatusReserva;
import br.ufes.reserva_espacos.repositories.EspacoRepository;
import br.ufes.reserva_espacos.repositories.ReservaRepository;
import br.ufes.reserva_espacos.repositories.SolicitanteRepository;
import jakarta.transaction.Transactional;

@Service
public class ReservaService {

	private final ReservaRepository reservaRepository;
	private final SolicitanteRepository solicitanteRepository;
	private final EspacoRepository espacoRepository;

	public ReservaService(ReservaRepository reservaRepository,
			SolicitanteRepository solicitanteRepository,
			EspacoRepository espacoRepository) {
		this.reservaRepository = reservaRepository;
		this.solicitanteRepository = solicitanteRepository;
		this.espacoRepository = espacoRepository;
	}

	@Transactional
	public Reserva cadastrar(CadastroReservaDTO dto) {
		Solicitante solicitante = solicitanteRepository.findById(dto.getIdSolicitante())
				.orElseThrow(() -> new RuntimeException("Solicitante não encontrado."));

		Espaco espaco = espacoRepository.findById(dto.getIdEspaco())
				.orElseThrow(() -> new RuntimeException("Espaço não encontrado."));

		validarReserva(dto, espaco, null);

		Reserva reserva = new Reserva();
		preencherReserva(reserva, dto, solicitante, espaco);
		reserva.setDtSolicitacao(LocalDateTime.now());
		reserva.setStatus(StatusReserva.EM_ANALISE);
		return reservaRepository.save(reserva);
	}

	public List<Reserva> listar() {
		return reservaRepository.findAllByOrderByDtSolicitacaoDesc();
	}

	public List<Reserva> listarPorSolicitante(Integer idSolicitante) {
		return reservaRepository.findBySolicitante_IdSolicitanteOrderByDtSolicitacaoDesc(idSolicitante);
	}

	public List<Reserva> listarPorStatus(StatusReserva status) {
		return reservaRepository.findByStatusOrderByDtSolicitacaoDesc(status);
	}

	public List<ReservaResponseDTO> listarDTO(List<Reserva> reservas) {
		return reservas.stream().map(ReservaResponseDTO::fromEntity).collect(Collectors.toList());
	}

	public Reserva buscarPorId(Integer id) {
		return reservaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Reserva não encontrada."));
	}

	@Transactional
	public void excluir(Integer id) {
		if (!reservaRepository.existsById(id)) {
			throw new RuntimeException("Reserva não encontrada.");
		}

		reservaRepository.deleteById(id);
	}

	@Transactional
	public Reserva atualizar(Integer id, CadastroReservaDTO dto) {
		Reserva reserva = buscarPorId(id);

		Solicitante solicitante = solicitanteRepository.findById(dto.getIdSolicitante())
				.orElseThrow(() -> new RuntimeException("Solicitante não encontrado."));

		Espaco espaco = espacoRepository.findById(dto.getIdEspaco())
				.orElseThrow(() -> new RuntimeException("Espaço não encontrado."));

		validarReserva(dto, espaco, id);
		preencherReserva(reserva, dto, solicitante, espaco);
		return reservaRepository.save(reserva);
	}

	@Transactional
	public Reserva cancelar(Integer id, CancelarReservaDTO dto) {
		Reserva reserva = buscarPorId(id);

		if (dto.getIdSolicitante() == null || reserva.getSolicitante() == null
				|| !dto.getIdSolicitante().equals(reserva.getSolicitante().getId())) {
			throw new RuntimeException("Apenas o solicitante que fez a reserva pode cancelá-la.");
		}

		if (reserva.getStatus() == StatusReserva.CANCELADA) {
			throw new RuntimeException("Reserva já está cancelada.");
		}

		if (reserva.getStatus() == StatusReserva.REJEITADA) {
			throw new RuntimeException("Reserva rejeitada não pode ser cancelada.");
		}

		reserva.setStatus(StatusReserva.CANCELADA);
		reserva.setDtCancelamento(LocalDateTime.now());
		return reservaRepository.save(reserva);
	}

	@Transactional
	public void aprovar(Reserva reserva) {
		reserva.setStatus(StatusReserva.APROVADA);
		reservaRepository.save(reserva);
	}

	@Transactional
	public void rejeitar(Reserva reserva) {
		reserva.setStatus(StatusReserva.REJEITADA);
		reservaRepository.save(reserva);
	}

	public boolean existeConflitoDeAprovacao(Integer idEspaco, LocalDate dtUso, java.time.LocalTime horaInicio,
			java.time.LocalTime horaFim, Integer idReservaAtual) {
		return reservaRepository.existsByEspaco_IdEspacoAndDtUsoAndStatusAndHoraInicioLessThanAndHoraFimGreaterThanAndIdReservaNot(
				idEspaco, dtUso, StatusReserva.APROVADA, horaFim, horaInicio, idReservaAtual);
	}

	private void preencherReserva(Reserva reserva, CadastroReservaDTO dto, Solicitante solicitante, Espaco espaco) {
		reserva.setSolicitante(solicitante);
		reserva.setEspaco(espaco);
		reserva.setHoraInicio(dto.getHoraInicio());
		reserva.setDtUso(dto.getDtUso());
		reserva.setHoraFim(dto.getHoraFim());
		reserva.setFinalidade(dto.getFinalidade());
		reserva.setDtCancelamento(dto.getDtCancelamento());
		reserva.setDtConfirmacao(dto.getDtConfirmacao());
		reserva.setQtdPessoas(dto.getQtdPessoas());
		if (dto.getStatus() != null) {
			reserva.setStatus(dto.getStatus());
		}
	}

	private void validarReserva(CadastroReservaDTO dto, Espaco espaco, Integer idReservaAtual) {
		if (dto.getDtUso() == null || dto.getHoraInicio() == null || dto.getHoraFim() == null) {
			throw new RuntimeException("Data e horários da reserva são obrigatórios.");
		}

		if (!dto.getDtUso().isAfter(LocalDate.now())) {
			throw new RuntimeException("Reservas só podem ser realizadas para datas futuras.");
		}

		if (!dto.getHoraFim().isAfter(dto.getHoraInicio())) {
			throw new RuntimeException("O horário de término deve ser posterior ao horário de início.");
		}

		if (dto.getQtdPessoas() == null || dto.getQtdPessoas() <= 0) {
			throw new RuntimeException("A quantidade prevista de participantes deve ser maior que zero.");
		}

		if (espaco.getStatus() != StatusEspaco.ATIVO) {
			throw new RuntimeException("Espaço indisponível para reservas.");
		}

		if (dto.getQtdPessoas() > espaco.getCapacidade()) {
			throw new RuntimeException("A quantidade prevista de participantes não pode ultrapassar a capacidade do espaço.");
		}

		StatusReserva status = dto.getStatus() != null ? dto.getStatus() : StatusReserva.EM_ANALISE;
		if (status == StatusReserva.APROVADA) {
			boolean conflito = idReservaAtual == null
					? reservaRepository.existsByEspaco_IdEspacoAndDtUsoAndStatusAndHoraInicioLessThanAndHoraFimGreaterThan(
							espaco.getIdEspaco(), dto.getDtUso(), StatusReserva.APROVADA, dto.getHoraFim(), dto.getHoraInicio())
					: reservaRepository.existsByEspaco_IdEspacoAndDtUsoAndStatusAndHoraInicioLessThanAndHoraFimGreaterThanAndIdReservaNot(
							espaco.getIdEspaco(), dto.getDtUso(), StatusReserva.APROVADA, dto.getHoraFim(), dto.getHoraInicio(), idReservaAtual);

			if (conflito) {
				throw new RuntimeException("Já existe reserva aprovada para o mesmo espaço e horário.");
			}
		}
	}
}
