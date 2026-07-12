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
@Table(name = "analisereserva")
public class AnaliseReserva {

    public AnaliseReserva() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numanalise", nullable = false)
    private Integer numAnalise;

    public Integer getNumAnalise() {
        return numAnalise;
    }

    public void setNumAnalise(Integer numAnalise) {
        this.numAnalise = numAnalise;
    }

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Column(name = "dtanalise", nullable = false)
    private LocalDate dtAnalise;

    public LocalDate getDtAnalise() {
        return dtAnalise;
    }

    public void setDtAnalise(LocalDate dtAnalise) {
        this.dtAnalise = dtAnalise;
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

    @ManyToOne
    @JoinColumn(name = "idadministrador", nullable = false)
    private Administrador administrador;

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
}
