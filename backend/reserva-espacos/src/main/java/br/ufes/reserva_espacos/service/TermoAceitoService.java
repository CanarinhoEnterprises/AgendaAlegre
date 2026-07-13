package br.ufes.reserva_espacos.service;

import java.time.LocalDateTime;
import java.util.List;
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
import jakarta.transaction.Transactional;

@Service
public class TermoAceitoService {
    private final TermoAceitoRepository termoAceitoRepository;
    private final ReservaRepository reservaRepository;
    private final ModeloTermoRepository modeloTermoRepository;
    private final ReservaService reservaService;

    public TermoAceitoService(TermoAceitoRepository termoAceitoRepository, ReservaRepository reservaRepository,
                               ModeloTermoRepository modeloTermoRepository, ReservaService reservaService) {
        this.termoAceitoRepository = termoAceitoRepository;
        this.reservaRepository = reservaRepository;
        this.modeloTermoRepository = modeloTermoRepository;
        this.reservaService = reservaService;
    }

    @Transactional
    public TermoAceito cadastrar(CadastroTermoAceitoDTO dto) {
        validarTermoAceito(dto);

        Reserva reserva = reservaRepository.findById(dto.getIdReserva())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        return gerarParaReserva(reserva, dto.getIdModeloTermo());
    }

    // RF21: geração automática do termo para reservas aprovadas
    @Transactional
    public TermoAceito gerarAutomaticoParaReserva(Reserva reserva) {
        return gerarParaReserva(reserva, null);
    }

    private TermoAceito gerarParaReserva(Reserva reserva, Integer idModeloTermo) {
        // RN05
        if (reserva.getStatus() != StatusReserva.APROVADA && reserva.getStatus() != StatusReserva.AGUARDANDO_TERMO) {
            throw new RuntimeException("Termo pode ser gerado apenas para reserva aprovada");
        }

        // RN06
        Optional<TermoAceito> termoExistente = termoAceitoRepository.findByReserva_IdReserva(reserva.getIdReserva());
        if (termoExistente.isPresent()) {
            throw new RuntimeException("Já existe termo para esta reserva");
        }

        TermoAceito termo = new TermoAceito();
        termo.setReserva(reserva);
        termo.setDtGeracao(LocalDateTime.now());
        termo.setDtAceite(null);

        ModeloTermo modelo = null;
        if (idModeloTermo != null) {
            modelo = modeloTermoRepository.findById(idModeloTermo).orElse(null);
        }
        if (modelo == null) {
            List<ModeloTermo> modelos = modeloTermoRepository.findAllByOrderByVersaoDesc();
            if (!modelos.isEmpty()) {
                modelo = modelos.get(0);
            }
        }
        termo.setModeloTermo(modelo);

        TermoAceito salvo = termoAceitoRepository.save(termo);

        reservaService.aprovarComVinculoDeTermo(reserva);

        return salvo;
    }

    public Optional<TermoAceito> consultarPorReserva(Integer idReserva) {
        return termoAceitoRepository.findByReserva_IdReserva(idReserva);
    }

    public Optional<TermoAceito> consultar(Integer id) {
        return termoAceitoRepository.findById(id);
    }

    // RF22
    @Transactional
    public TermoAceito aceitar(Integer id) {
        TermoAceito termo = termoAceitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Termo não encontrado"));

        if (termo.getDtAceite() != null) {
            throw new RuntimeException("Termo já foi aceito");
        }

        Reserva reserva = termo.getReserva();
        if (reserva.getStatus() != StatusReserva.AGUARDANDO_TERMO) {
            throw new RuntimeException("Reserva não está aguardando aceite de termo");
        }

        termo.setDtAceite(LocalDateTime.now());
        TermoAceito salvo = termoAceitoRepository.save(termo);

        reservaService.confirmar(reserva);

        return salvo;
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
