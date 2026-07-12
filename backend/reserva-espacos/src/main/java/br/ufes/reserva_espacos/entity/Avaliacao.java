package br.ufes.reserva_espacos.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "avaliacao")
public class Avaliacao {

    public Avaliacao() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idavaliacao", nullable = false)
    private Integer idAvaliacao;

    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    @ManyToOne
    @JoinColumn(name = "idreserva", nullable = false)
    private Reserva reserva;

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Column(name = "nota", nullable = false)
    private Integer nota;

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    @Column(name = "comentario", nullable = false, columnDefinition = "TEXT")
    private String comentario;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Column(name = "dtavaliacao", nullable = false)
    private LocalDate dtAvaliacao;

    public LocalDate getDtAvaliacao() {
        return dtAvaliacao;
    }

    public void setDtAvaliacao(LocalDate dtAvaliacao) {
        this.dtAvaliacao = dtAvaliacao;
    }
}
