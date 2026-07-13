package br.ufes.reserva_espacos.dto.reservadto;

public class CancelarReservaDTO {
    private Integer idSolicitante;
    private String motivo;

    public CancelarReservaDTO() {
    }

    public Integer getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Integer idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
