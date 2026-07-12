package br.ufes.reserva_espacos.dto.ocorrenciadto;

import java.time.LocalDate;

import br.ufes.reserva_espacos.enums.StatusOcorrencia;
import br.ufes.reserva_espacos.enums.TipoOcorrencia;

public class CadastroOcorrenciaDTO {
    private String titulo;
    private String descricao;
    private LocalDate dtRegistro;
    private TipoOcorrencia tipo;
    private Integer idEspaco;
    private StatusOcorrencia status;

    public CadastroOcorrenciaDTO() {
    }

    public CadastroOcorrenciaDTO(String titulo, String descricao, LocalDate dtRegistro, TipoOcorrencia tipo, Integer idEspaco, StatusOcorrencia status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dtRegistro = dtRegistro;
        this.tipo = tipo;
        this.idEspaco = idEspaco;
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDtRegistro() {
        return dtRegistro;
    }

    public void setDtRegistro(LocalDate dtRegistro) {
        this.dtRegistro = dtRegistro;
    }

    public TipoOcorrencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoOcorrencia tipo) {
        this.tipo = tipo;
    }

    public Integer getIdEspaco() {
        return idEspaco;
    }

    public void setIdEspaco(Integer idEspaco) {
        this.idEspaco = idEspaco;
    }

    public StatusOcorrencia getStatus() {
        return status;
    }

    public void setStatus(StatusOcorrencia status) {
        this.status = status;
    }
}
