package br.ufes.reserva_espacos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.avaliacaodto.AvaliacaoResponseDTO;
import br.ufes.reserva_espacos.dto.avaliacaodto.CadastroAvaliacaoDTO;
import br.ufes.reserva_espacos.entity.Avaliacao;
import br.ufes.reserva_espacos.entity.Reserva;
import br.ufes.reserva_espacos.enums.StatusReserva;
import br.ufes.reserva_espacos.repositories.AvaliacaoRepository;
import br.ufes.reserva_espacos.repositories.ReservaRepository;

@Service
public class AvaliacaoService {
    private final AvaliacaoRepository avaliacaoRepository;
    private final ReservaRepository reservaRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, ReservaRepository reservaRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.reservaRepository = reservaRepository;
    }

    public Avaliacao cadastrar(CadastroAvaliacaoDTO dto) {
        validarAvaliacao(dto);

        Optional<Reserva> reserva = reservaRepository.findById(dto.getIdReserva());
        if (reserva.isEmpty()) {
            throw new RuntimeException("Reserva não encontrada");
        }

        // RN19: Apenas reservas confirmadas (usadas) podem ser avaliadas
        Reserva r = reserva.get();
        if (r.getStatus() != StatusReserva.CONFIRMADA) {
            throw new RuntimeException("Apenas reservas confirmadas podem ser avaliadas");
        }

        // RN09: Uma avaliação por reserva
        Optional<Avaliacao> avaliacaoExistente = avaliacaoRepository.findByReserva_IdReserva(dto.getIdReserva());
        if (avaliacaoExistente.isPresent()) {
            throw new RuntimeException("Já existe avaliação para esta reserva");
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setReserva(r);
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setDtAvaliacao(dto.getDtAvaliacao() != null ? dto.getDtAvaliacao() : LocalDate.now());

        return avaliacaoRepository.save(avaliacao);
    }

    public List<AvaliacaoResponseDTO> buscarTodas() {
    return avaliacaoRepository.findAll().stream()
        .map(avaliacao -> {
            var solicitante = avaliacao.getReserva().getSolicitante();
            String nome = (solicitante != null && solicitante.getUsuario() != null) 
                          ? solicitante.getUsuario().getNome() 
                          : "Usuário Desconhecido";
            
            // Forçamos a conversão para Long usando .longValue()
            return new AvaliacaoResponseDTO(
                avaliacao.getIdAvaliacao() != null ? avaliacao.getIdAvaliacao().longValue() : null,
                avaliacao.getReserva().getIdReserva() != null ? avaliacao.getReserva().getIdReserva().longValue() : null,
                avaliacao.getReserva().getEspaco().getNome(),
                avaliacao.getReserva().getEspaco().getTipoEspaco().getNome(),
                avaliacao.getReserva().getEspaco().getEndereco().getLogradouro(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getDtAvaliacao(),
                nome,
                avaliacao.getReserva().getEspaco().getUrlCapa()
            );
        })
        .collect(Collectors.toList());
    }

    public List<AvaliacaoResponseDTO> buscarPorSolicitante(Integer idSolicitante) {
    return avaliacaoRepository.findByReserva_Solicitante_Id(idSolicitante)
        .stream()
        .map(AvaliacaoResponseDTO::from)
        .toList();
}

    public Optional<Avaliacao> consultarPorReserva(Integer idReserva) {
        return avaliacaoRepository.findByReserva_IdReserva(idReserva);
    }

    public Optional<Avaliacao> consultar(Integer id) {
        return avaliacaoRepository.findById(id);
    }

    public Avaliacao atualizar(Integer id, CadastroAvaliacaoDTO dto) {
        validarAvaliacao(dto);

        Optional<Avaliacao> avaliacao = avaliacaoRepository.findById(id);
        if (avaliacao.isEmpty()) {
            throw new RuntimeException("Avaliação não encontrada");
        }

        Avaliacao aval = avaliacao.get();
        aval.setNota(dto.getNota());
        aval.setComentario(dto.getComentario());
        aval.setDtAvaliacao(dto.getDtAvaliacao());

        return avaliacaoRepository.save(aval);
    }

    public void excluir(Integer id) {
        Optional<Avaliacao> avaliacao = avaliacaoRepository.findById(id);
        if (avaliacao.isEmpty()) {
            throw new RuntimeException("Avaliação não encontrada");
        }
        avaliacaoRepository.deleteById(id);
    }

    private void validarAvaliacao(CadastroAvaliacaoDTO dto) {
        if (dto.getIdReserva() == null) {
            throw new RuntimeException("ID da reserva é obrigatório");
        }
        if (dto.getNota() == null || dto.getNota() < 1 || dto.getNota() > 5) {
            throw new RuntimeException("Nota deve ser um valor entre 1 e 5");
        }
        if (dto.getComentario() == null || dto.getComentario().trim().isEmpty()) {
            throw new RuntimeException("Comentário é obrigatório");
        }
        if (dto.getComentario().length() > 1000) {
            throw new RuntimeException("Comentário não pode exceder 1000 caracteres");
        }
    }
}
