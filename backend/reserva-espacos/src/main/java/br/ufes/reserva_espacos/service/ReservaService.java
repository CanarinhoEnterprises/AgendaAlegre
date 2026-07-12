package br.ufes.reserva_espacos.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.reservadto.CadastroReservaDTO;
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
		return reservaRepository.save(reserva);
	}

	public List<Reserva> listar() {
		return reservaRepository.findAllByOrderByDtSolicitacaoDesc();
	}

	public List<Reserva> listarPorSolicitante(Integer idSolicitante) {
		return reservaRepository.findBySolicitante_IdSolicitanteOrderByDtSolicitacaoDesc(idSolicitante);
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
		reserva.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusReserva.DOCUMENTOS_PENDENTES);
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

		StatusReserva status = dto.getStatus() != null ? dto.getStatus() : StatusReserva.DOCUMENTOS_PENDENTES;
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
