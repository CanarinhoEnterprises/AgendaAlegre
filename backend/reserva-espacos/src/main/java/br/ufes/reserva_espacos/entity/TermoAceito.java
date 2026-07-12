package br.ufes.reserva_espacos.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "termoaceitom")
public class TermoAceito {

    public TermoAceito() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numtermo", nullable = false)
    private Integer numTermo;

    public Integer getNumTermo() {
        return numTermo;
    }

    public void setNumTermo(Integer numTermo) {
        this.numTermo = numTermo;
    }

    @OneToOne
    @JoinColumn(name = "idreserva", nullable = false, unique = true)
    private Reserva reserva;

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Column(name = "dtgeracao", nullable = false)
    private LocalDateTime dtGeracao;

    public LocalDateTime getDtGeracao() {
        return dtGeracao;
    }

    public void setDtGeracao(LocalDateTime dtGeracao) {
        this.dtGeracao = dtGeracao;
    }

    @Column(name = "dtaceite")
    private LocalDateTime dtAceite;

    public LocalDateTime getDtAceite() {
        return dtAceite;
    }

    public void setDtAceite(LocalDateTime dtAceite) {
        this.dtAceite = dtAceite;
    }

    @ManyToOne
    @JoinColumn(name = "idmodelotermo")
    private ModeloTermo modeloTermo;

    public ModeloTermo getModeloTermo() {
        return modeloTermo;
    }

    public void setModeloTermo(ModeloTermo modeloTermo) {
        this.modeloTermo = modeloTermo;
    }
}
