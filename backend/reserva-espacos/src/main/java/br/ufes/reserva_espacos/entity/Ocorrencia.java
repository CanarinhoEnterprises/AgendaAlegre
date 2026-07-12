package br.ufes.reserva_espacos.entity;

import java.time.LocalDate;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import br.ufes.reserva_espacos.enums.StatusOcorrencia;
import br.ufes.reserva_espacos.enums.TipoOcorrencia;
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
@Table(name = "ocorrencia")
public class Ocorrencia {

    public Ocorrencia() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idocorrencia", nullable = false)
    private Integer idOcorrencia;

    public Integer getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Integer idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name = "dtregistro", nullable = false)
    private LocalDate dtRegistro;

    public LocalDate getDtRegistro() {
        return dtRegistro;
    }

    public void setDtRegistro(LocalDate dtRegistro) {
        this.dtRegistro = dtRegistro;
    }

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, columnDefinition = "tipo_ocorrencia")
    private TipoOcorrencia tipo;

    public TipoOcorrencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoOcorrencia tipo) {
        this.tipo = tipo;
    }

    @ManyToOne
    @JoinColumn(name = "idespaco", nullable = false)
    private Espaco espaco;

    public Espaco getEspaco() {
        return espaco;
    }

    public void setEspaco(Espaco espaco) {
        this.espaco = espaco;
    }

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "status_ocorrencia")
    private StatusOcorrencia status;

    public StatusOcorrencia getStatus() {
        return status;
    }

    public void setStatus(StatusOcorrencia status) {
        this.status = status;
    }
}
