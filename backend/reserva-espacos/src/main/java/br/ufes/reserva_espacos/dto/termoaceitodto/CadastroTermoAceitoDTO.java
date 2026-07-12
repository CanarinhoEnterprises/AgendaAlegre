package br.ufes.reserva_espacos.dto.termoaceitodto;

import java.time.LocalDateTime;

public class CadastroTermoAceitoDTO {
    private Integer idReserva;
    private LocalDateTime dtGeracao;
    private LocalDateTime dtAceite;
    private Integer idModeloTermo;

    public CadastroTermoAceitoDTO() {
    }

    public CadastroTermoAceitoDTO(Integer idReserva, LocalDateTime dtGeracao, LocalDateTime dtAceite, Integer idModeloTermo) {
        this.idReserva = idReserva;
        this.dtGeracao = dtGeracao;
        this.dtAceite = dtAceite;
        this.idModeloTermo = idModeloTermo;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDateTime getDtGeracao() {
        return dtGeracao;
    }

    public void setDtGeracao(LocalDateTime dtGeracao) {
        this.dtGeracao = dtGeracao;
    }

    public LocalDateTime getDtAceite() {
        return dtAceite;
    }

    public void setDtAceite(LocalDateTime dtAceite) {
        this.dtAceite = dtAceite;
    }

    public Integer getIdModeloTermo() {
        return idModeloTermo;
    }

    public void setIdModeloTermo(Integer idModeloTermo) {
        this.idModeloTermo = idModeloTermo;
    }
}
