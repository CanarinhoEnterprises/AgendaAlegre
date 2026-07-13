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

    public AnaliseReservaService(AnaliseReservaRepository analiseReservaRepository, ReservaRepository reservaRepository,
                                  AdministradorRepository administradorRepository, ReservaService reservaService) {
        this.analiseReservaRepository = analiseReservaRepository;
        this.reservaRepository = reservaRepository;
        this.administradorRepository = administradorRepository;
        this.reservaService = reservaService;
    }

    @Transactional
    public AnaliseReserva cadastrar(CadastroAnaliseReservaDTO dto) {
        validarAnaliseReserva(dto);

        Reserva reserva = reservaRepository.findById(dto.getIdReserva())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        if (reserva.getStatus() != StatusReserva.EM_ANALISE) {
            throw new RuntimeException("Reserva não está em análise");
        }

        if (analiseReservaRepository.findByReserva_IdReserva(dto.getIdReserva()).isPresent()) {
            throw new RuntimeException("Reserva já possui uma análise");
        }

        Administrador admin = administradorRepository.findById(dto.getIdAdministrador())
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado"));

        LocalDate dtAnalise = dto.getDtAnalise() != null ? dto.getDtAnalise() : LocalDate.now();

        if (Boolean.FALSE.equals(dto.getAprovado()) && (dto.getObservacao() == null || dto.getObservacao().trim().isEmpty())) {
            throw new RuntimeException("O motivo da rejeição é obrigatório.");
        }

        AnaliseReserva analise = new AnaliseReserva();
        analise.setObservacao(dto.getObservacao());
        analise.setDtAnalise(dtAnalise);
        analise.setReserva(reserva);
        analise.setAdministrador(admin);

        AnaliseReserva salva = analiseReservaRepository.save(analise);

        if (Boolean.TRUE.equals(dto.getAprovado())) {

    boolean conflito = reservaService.existeConflitoDeAprovacao(
            reserva.getEspaco().getIdEspaco(),
            reserva.getDtUso(),
            reserva.getHoraInicio(),
            reserva.getHoraFim(),
            reserva.getIdReserva());

    if (conflito) {
        throw new RuntimeException(
                "Já existe uma reserva aprovada para este espaço nesse horário.");
    }

    reservaService.aprovar(reserva);

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

    public AnaliseReserva atualizar(Integer id, CadastroAnaliseReservaDTO dto) {
        validarAnaliseReserva(dto);

        Optional<AnaliseReserva> analise = analiseReservaRepository.findById(id);
        if (analise.isEmpty()) {
            throw new RuntimeException("Análise de reserva não encontrada");
        }

        Optional<Administrador> admin = administradorRepository.findById(dto.getIdAdministrador());
        if (admin.isEmpty()) {
            throw new RuntimeException("Administrador não encontrado");
        }

        AnaliseReserva ana = analise.get();
        ana.setObservacao(dto.getObservacao());
        ana.setDtAnalise(dto.getDtAnalise());
        ana.setAdministrador(admin.get());

        return analiseReservaRepository.save(ana);
    }

    public void excluir(Integer id) {
        Optional<AnaliseReserva> analise = analiseReservaRepository.findById(id);
        if (analise.isEmpty()) {
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
