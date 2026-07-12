package br.ufes.reserva_espacos.dto.pendenciadto;

import br.ufes.reserva_espacos.enums.StatusPendencia;
import br.ufes.reserva_espacos.enums.TipoPendencia;

public class CadastroPendenciaDTO {
    private Integer idReserva;
    private Integer idTipoDoc;
    private String descricao;
    private String obsUsuario;
    private Integer idAdministrador;
    private TipoPendencia tipo;
    private StatusPendencia status;

    public CadastroPendenciaDTO() {
    }

    public CadastroPendenciaDTO(Integer idReserva, Integer idTipoDoc, String descricao, String obsUsuario, 
                                Integer idAdministrador, TipoPendencia tipo, StatusPendencia status) {
        this.idReserva = idReserva;
        this.idTipoDoc = idTipoDoc;
        this.descricao = descricao;
        this.obsUsuario = obsUsuario;
        this.idAdministrador = idAdministrador;
        this.tipo = tipo;
        this.status = status;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Integer getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(Integer idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObsUsuario() {
        return obsUsuario;
    }

    public void setObsUsuario(String obsUsuario) {
        this.obsUsuario = obsUsuario;
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public TipoPendencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoPendencia tipo) {
        this.tipo = tipo;
    }

    public StatusPendencia getStatus() {
        return status;
    }

    public void setStatus(StatusPendencia status) {
        this.status = status;
    }
}
