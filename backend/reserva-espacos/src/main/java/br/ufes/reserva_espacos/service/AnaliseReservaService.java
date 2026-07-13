package br.ufes.reserva_espacos.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.analisereservadto.CadastroAnaliseReservaDTO;
import br.ufes.reserva_espacos.entity.Administrador;
import br.ufes.reserva_espacos.entity.AnaliseReserva;
import br.ufes.reserva_espacos.entity.Reserva;
import br.ufes.reserva_espacos.enums.StatusReserva;
import br.ufes.reserva_espacos.repositories.AdministradorRepository;
import br.ufes.reserva_espacos.repositories.AnaliseReservaRepository;
import br.ufes.reserva_espacos.repositories.ReservaRepository;
import jakarta.transaction.Transactional;

@Service
public class AnaliseReservaService {
    private final AnaliseReservaRepository analiseReservaRepository;
    private final ReservaRepository reservaRepository;
    private final AdministradorRepository administradorRepository;
    private final ReservaService reservaService;
    private final TermoAceitoService termoAceitoService;

    public AnaliseReservaService(AnaliseReservaRepository analiseReservaRepository, ReservaRepository reservaRepository,
                                  AdministradorRepository administradorRepository, ReservaService reservaService,
                                  TermoAceitoService termoAceitoService) {
        this.analiseReservaRepository = analiseReservaRepository;
        this.reservaRepository = reservaRepository;
        this.administradorRepository = administradorRepository;
        this.reservaService = reservaService;
        this.termoAceitoService = termoAceitoService;
    }

    @Transactional
    public AnaliseReserva cadastrar(CadastroAnaliseReservaDTO dto) {
        validarAnaliseReserva(dto);

        Reserva reserva = reservaRepository.findById(dto.getIdReserva())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        // RN03/precondição de fluxo
        if (reserva.getStatus() != StatusReserva.EM_ANALISE) {
            throw new RuntimeException("Reserva não está em análise");
        }

        // RN07
        if (analiseReservaRepository.findByReserva_IdReserva(dto.getIdReserva()).isPresent()) {
            throw new RuntimeException("Reserva já possui uma análise");
        }

        // RN04
        Administrador admin = administradorRepository.findById(dto.getIdAdministrador())
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado"));

        LocalDate dtAnalise = dto.getDtAnalise() != null ? dto.getDtAnalise() : LocalDate.now();

        // RN17
        if (dtAnalise.isBefore(reserva.getDtSolicitacao().toLocalDate())) {
            throw new RuntimeException("A data da análise não pode ser anterior à data da solicitação.");
        }

        // RN16
        if (Boolean.FALSE.equals(dto.getAprovado())
                && (dto.getObservacao() == null || dto.getObservacao().trim().isEmpty())) {
            throw new RuntimeException("O motivo da rejeição é obrigatório.");
        }

        // RN01, checado no momento da aprovação
        if (Boolean.TRUE.equals(dto.getAprovado())) {
            boolean conflito = reservaService.existeConflitoDeAprovacao(
                    reserva.getEspaco().getIdEspaco(), reserva.getDtUso(),
                    reserva.getHoraInicio(), reserva.getHoraFim(), reserva.getIdReserva());
            if (conflito) {
                throw new RuntimeException("Já existe reserva aprovada para o mesmo espaço e horário.");
            }
        }

        AnaliseReserva analise = new AnaliseReserva();
        analise.setObservacao(dto.getObservacao());
        analise.setDtAnalise(dtAnalise);
        analise.setReserva(reserva);
        analise.setAdministrador(admin);

        AnaliseReserva salva = analiseReservaRepository.save(analise);

        if (Boolean.TRUE.equals(dto.getAprovado())) {
            reservaService.aprovar(reserva);
            termoAceitoService.gerarAutomaticoParaReserva(reserva);
        } else {
            reservaService.rejeitar(reserva);
        }

        return salva;
    }

    public Optional<AnaliseReserva> consultarPorReserva(Integer idReserva) {
        return analiseReservaRepository.findByReserva_IdReserva(idReserva);
    }

    public Optional<AnaliseReserva> consultar(Integer id) {
        return analiseReservaRepository.findById(id);
    }

    @Transactional
    public AnaliseReserva atualizar(Integer id, CadastroAnaliseReservaDTO dto) {
        AnaliseReserva analise = analiseReservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Análise de reserva não encontrada"));

        Administrador admin = administradorRepository.findById(dto.getIdAdministrador())
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado"));

        if (dto.getObservacao() != null) {
            analise.setObservacao(dto.getObservacao());
        }
        if (dto.getDtAnalise() != null) {
            analise.setDtAnalise(dto.getDtAnalise());
        }
        analise.setAdministrador(admin);

        return analiseReservaRepository.save(analise);
    }

    @Transactional
    public void excluir(Integer id) {
        if (!analiseReservaRepository.existsById(id)) {
            throw new RuntimeException("Análise de reserva não encontrada");
        }
        analiseReservaRepository.deleteById(id);
    }

    private void validarAnaliseReserva(CadastroAnaliseReservaDTO dto) {
        if (dto.getIdReserva() == null) {
            throw new RuntimeException("ID da reserva é obrigatório");
        }
        if (dto.getIdAdministrador() == null) {
            throw new RuntimeException("ID do administrador é obrigatório");
        }
        if (dto.getAprovado() == null) {
            throw new RuntimeException("É necessário informar se a reserva foi aprovada ou rejeitada");
        }
    }
}
