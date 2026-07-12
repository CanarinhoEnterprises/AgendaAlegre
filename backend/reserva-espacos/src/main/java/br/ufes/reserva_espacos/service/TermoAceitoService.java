package br.ufes.reserva_espacos.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.termoaceitodto.CadastroTermoAceitoDTO;
import br.ufes.reserva_espacos.entity.ModeloTermo;
import br.ufes.reserva_espacos.entity.Reserva;
import br.ufes.reserva_espacos.entity.TermoAceito;
import br.ufes.reserva_espacos.enums.StatusReserva;
import br.ufes.reserva_espacos.repositories.ModeloTermoRepository;
import br.ufes.reserva_espacos.repositories.ReservaRepository;
import br.ufes.reserva_espacos.repositories.TermoAceitoRepository;

@Service
public class TermoAceitoService {
    private final TermoAceitoRepository termoAceitoRepository;
    private final ReservaRepository reservaRepository;
    private final ModeloTermoRepository modeloTermoRepository;

    public TermoAceitoService(TermoAceitoRepository termoAceitoRepository, ReservaRepository reservaRepository,
                               ModeloTermoRepository modeloTermoRepository) {
        this.termoAceitoRepository = termoAceitoRepository;
        this.reservaRepository = reservaRepository;
        this.modeloTermoRepository = modeloTermoRepository;
    }

    public TermoAceito cadastrar(CadastroTermoAceitoDTO dto) {
        validarTermoAceito(dto);

        Optional<Reserva> reserva = reservaRepository.findById(dto.getIdReserva());
        if (reserva.isEmpty()) {
            throw new RuntimeException("Reserva não encontrada");
        }

        // RN05: Termo deve ser para reserva aprovada
        if (reserva.get().getStatus() != StatusReserva.APROVADA) {
            throw new RuntimeException("Termo pode ser aceito apenas para reserva aprovada");
        }

        // RN06: Apenas um termo por reserva
        Optional<TermoAceito> termoExistente = termoAceitoRepository.findByReserva_IdReserva(dto.getIdReserva());
        if (termoExistente.isPresent()) {
            throw new RuntimeException("Já existe termo aceito para esta reserva");
        }

        TermoAceito termo = new TermoAceito();
        termo.setReserva(reserva.get());
        termo.setDtGeracao(LocalDateTime.now());
        termo.setDtAceite(null);

        if (dto.getIdModeloTermo() != null) {
            Optional<ModeloTermo> modelo = modeloTermoRepository.findById(dto.getIdModeloTermo());
            if (modelo.isPresent()) {
                termo.setModeloTermo(modelo.get());
            }
        }

        return termoAceitoRepository.save(termo);
    }

    public Optional<TermoAceito> consultarPorReserva(Integer idReserva) {
        return termoAceitoRepository.findByReserva_IdReserva(idReserva);
    }

    public Optional<TermoAceito> consultar(Integer id) {
        return termoAceitoRepository.findById(id);
    }

    public TermoAceito aceitar(Integer id) {
        Optional<TermoAceito> termo = termoAceitoRepository.findById(id);
        if (termo.isEmpty()) {
            throw new RuntimeException("Termo não encontrado");
        }

        TermoAceito t = termo.get();
        if (t.getDtAceite() != null) {
            throw new RuntimeException("Termo já foi aceito");
        }

        t.setDtAceite(LocalDateTime.now());
        return termoAceitoRepository.save(t);
    }

    public void excluir(Integer id) {
        Optional<TermoAceito> termo = termoAceitoRepository.findById(id);
        if (termo.isEmpty()) {
            throw new RuntimeException("Termo não encontrado");
        }
        termoAceitoRepository.deleteById(id);
    }

    private void validarTermoAceito(CadastroTermoAceitoDTO dto) {
        if (dto.getIdReserva() == null) {
            throw new RuntimeException("ID da reserva é obrigatório");
        }
    }
}
