package br.ufes.reserva_espacos.dto.analisereservadto;

import java.time.LocalDate;

public class CadastroAnaliseReservaDTO {
    private String observacao;
    private LocalDate dtAnalise;
    private Integer idReserva;
    private Integer idAdministrador;
    private Boolean aprovado;

    public CadastroAnaliseReservaDTO() {
    }

    public CadastroAnaliseReservaDTO(String observacao, LocalDate dtAnalise, Integer idReserva, Integer idAdministrador, Boolean aprovado) {
        this.observacao = observacao;
        this.dtAnalise = dtAnalise;
        this.idReserva = idReserva;
        this.idAdministrador = idAdministrador;
        this.aprovado = aprovado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDate getDtAnalise() {
        return dtAnalise;
    }

    public void setDtAnalise(LocalDate dtAnalise) {
        this.dtAnalise = dtAnalise;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public Boolean getAprovado() {
        return aprovado;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }
}
