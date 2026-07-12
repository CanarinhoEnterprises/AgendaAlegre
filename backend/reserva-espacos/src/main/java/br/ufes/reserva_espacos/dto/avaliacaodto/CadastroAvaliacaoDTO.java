package br.ufes.reserva_espacos.dto.avaliacaodto;

import java.time.LocalDate;

public class CadastroAvaliacaoDTO {
    private Integer idReserva;
    private Integer nota;
    private String comentario;
    private LocalDate dtAvaliacao;

    public CadastroAvaliacaoDTO() {
    }

    public CadastroAvaliacaoDTO(Integer idReserva, Integer nota, String comentario, LocalDate dtAvaliacao) {
        this.idReserva = idReserva;
        this.nota = nota;
        this.comentario = comentario;
        this.dtAvaliacao = dtAvaliacao;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getDtAvaliacao() {
        return dtAvaliacao;
    }

    public void setDtAvaliacao(LocalDate dtAvaliacao) {
        this.dtAvaliacao = dtAvaliacao;
    }
}
