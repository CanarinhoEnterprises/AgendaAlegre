package br.ufes.reserva_espacos.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.analisereservadto.CadastroAnaliseReservaDTO;
import br.ufes.reserva_espacos.entity.Administrador;
import br.ufes.reserva_espacos.entity.AnaliseReserva;
import br.ufes.reserva_espacos.entity.Reserva;
import br.ufes.reserva_espacos.repositories.AdministradorRepository;
import br.ufes.reserva_espacos.repositories.AnaliseReservaRepository;
import br.ufes.reserva_espacos.repositories.ReservaRepository;

@Service
public class AnaliseReservaService {
    private final AnaliseReservaRepository analiseReservaRepository;
    private final ReservaRepository reservaRepository;
    private final AdministradorRepository administradorRepository;

    public AnaliseReservaService(AnaliseReservaRepository analiseReservaRepository, ReservaRepository reservaRepository,
                                  AdministradorRepository administradorRepository) {
        this.analiseReservaRepository = analiseReservaRepository;
        this.reservaRepository = reservaRepository;
        this.administradorRepository = administradorRepository;
    }

    public AnaliseReserva cadastrar(CadastroAnaliseReservaDTO dto) {
        validarAnaliseReserva(dto);

        Optional<Reserva> reserva = reservaRepository.findById(dto.getIdReserva());
        if (reserva.isEmpty()) {
            throw new RuntimeException("Reserva não encontrada");
        }

        Optional<Administrador> admin = administradorRepository.findById(dto.getIdAdministrador());
        if (admin.isEmpty()) {
            throw new RuntimeException("Administrador não encontrado");
        }

        AnaliseReserva analise = new AnaliseReserva();
        analise.setObservacao(dto.getObservacao());
        analise.setDtAnalise(dto.getDtAnalise() != null ? dto.getDtAnalise() : LocalDate.now());
        analise.setReserva(reserva.get());
        analise.setAdministrador(admin.get());

        return analiseReservaRepository.save(analise);
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
    }
}
