package br.ufes.reserva_espacos.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "arquivo")
public class Arquivo {

    public Arquivo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idarquivo", nullable = false)
    private Integer idArquivo;

    public Integer getIdArquivo() {
        return idArquivo;
    }

    public void setIdArquivo(Integer idArquivo) {
        this.idArquivo = idArquivo;
    }

    @ManyToOne
    @JoinColumn(name = "idtipodoc", nullable = false)
    private TipoDoc tipoDoc;

    public TipoDoc getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    @ManyToOne
    @JoinColumn(name = "idpendencia", nullable = false)
    private Pendencia pendencia;

    public Pendencia getPendencia() {
        return pendencia;
    }

    public void setPendencia(Pendencia pendencia) {
        this.pendencia = pendencia;
    }

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "extensao", nullable = false, length = 20)
    private String extensao;

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    @Column(name = "tamanho", nullable = false)
    private Long tamanho;

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    @Column(name = "dtupload", nullable = false)
    private LocalDateTime dtUpload;

    public LocalDateTime getDtUpload() {
        return dtUpload;
    }

    public void setDtUpload(LocalDateTime dtUpload) {
        this.dtUpload = dtUpload;
    }
}
