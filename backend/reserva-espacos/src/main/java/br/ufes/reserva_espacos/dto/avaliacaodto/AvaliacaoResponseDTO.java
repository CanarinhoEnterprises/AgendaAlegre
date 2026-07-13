package br.ufes.reserva_espacos.dto.avaliacaodto;

import java.time.LocalDate;

import br.ufes.reserva_espacos.entity.Avaliacao;

public class AvaliacaoResponseDTO {
    
    private Long id;
    private Long reservaId;
    private String espacoNome;
    private String categoria;
    private String localizacao;
    private Integer nota;
    private String comentario;
    private LocalDate dataAvaliacao;
    
    private String nomeUsuario; 

    public AvaliacaoResponseDTO() {}

    public AvaliacaoResponseDTO(Long id, Long reservaId, String espacoNome, String categoria, 
                                String localizacao, Integer nota, String comentario, 
                                LocalDate dataAvaliacao, String nomeUsuario) {
        this.id = id;
        this.reservaId = reservaId;
        this.espacoNome = espacoNome;
        this.categoria = categoria;
        this.localizacao = localizacao;
        this.nota = nota;
        this.comentario = comentario;
        this.dataAvaliacao = dataAvaliacao;
        this.nomeUsuario = nomeUsuario;
    }

    public static AvaliacaoResponseDTO from(Avaliacao avaliacao) {
    return new AvaliacaoResponseDTO(
        avaliacao.getIdAvaliacao().longValue(),
        avaliacao.getReserva().getIdReserva().longValue(),
        avaliacao.getReserva().getEspaco().getNome(),
        avaliacao.getReserva().getEspaco().getTipoEspaco().getNome(),
        montarLocalizacao(avaliacao.getReserva().getEspaco().getEndereco()),
        avaliacao.getNota(),
        avaliacao.getComentario(),
        avaliacao.getDtAvaliacao(),
        avaliacao.getReserva().getSolicitante().getUsuario().getNome()
    );
    }

    private static String montarLocalizacao(br.ufes.reserva_espacos.entity.Endereco endereco) {
        if (endereco == null || endereco.getCidade() == null) {
            return "";
        }
        return endereco.getBairro() + " - " + endereco.getCidade().getNome() + "/" + endereco.getCidade().getUF();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getReservaId() { return reservaId; }
    public void setReservaId(Long reservaId) { this.reservaId = reservaId; }

    public String getEspacoNome() { return espacoNome; }
    public void setEspacoNome(String espacoNome) { this.espacoNome = espacoNome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDate getDataAvaliacao() { return dataAvaliacao; }
    public void setDataAvaliacao(LocalDate dataAvaliacao) { this.dataAvaliacao = dataAvaliacao; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
}