package br.ufes.reserva_espacos.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import br.ufes.reserva_espacos.enums.StatusPendencia;
import br.ufes.reserva_espacos.enums.TipoPendencia;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pendencia")
public class Pendencia {

    public Pendencia() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpendencia", nullable = false)
    private Integer idPendencia;

    public Integer getIdPendencia() {
        return idPendencia;
    }

    public void setIdPendencia(Integer idPendencia) {
        this.idPendencia = idPendencia;
    }

    @Column(name = "dtcriacao", nullable = false)
    private LocalDateTime dtCriacao;

    public LocalDateTime getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDateTime dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "status_pendencia")
    private StatusPendencia status;

    public StatusPendencia getStatus() {
        return status;
    }

    public void setStatus(StatusPendencia status) {
        this.status = status;
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
    @JoinColumn(name = "idtipodoc")
    private TipoDoc tipoDoc;

    public TipoDoc getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    @Column(name = "dtresolucao")
    private LocalDateTime dtResolucao;

    public LocalDateTime getDtResolucao() {
        return dtResolucao;
    }

    public void setDtResolucao(LocalDateTime dtResolucao) {
        this.dtResolucao = dtResolucao;
    }

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name = "obsUsuario", columnDefinition = "TEXT")
    private String obsUsuario;

    public String getObsUsuario() {
        return obsUsuario;
    }

    public void setObsUsuario(String obsUsuario) {
        this.obsUsuario = obsUsuario;
    }

    @ManyToOne
    @JoinColumn(name = "idadministrador")
    private Administrador administrador;

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, columnDefinition = "tipo_pendencia")
    private TipoPendencia tipo;

    public TipoPendencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoPendencia tipo) {
        this.tipo = tipo;
    }
}
