package br.ufes.reserva_espacos.dto.analisereservadto;

import java.time.LocalDate;

public class CadastroAnaliseReservaDTO {
    private String observacao;
    private LocalDate dtAnalise;
    private Integer idReserva;
    private Integer idAdministrador;

    public CadastroAnaliseReservaDTO() {
    }

    public CadastroAnaliseReservaDTO(String observacao, LocalDate dtAnalise, Integer idReserva, Integer idAdministrador) {
        this.observacao = observacao;
        this.dtAnalise = dtAnalise;
        this.idReserva = idReserva;
        this.idAdministrador = idAdministrador;
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
}
