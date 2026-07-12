package br.ufes.reserva_espacos.dto.recurscoacessibilidadedto;

import br.ufes.reserva_espacos.entity.RecursoAcessibilidade;

public class RecursoAcessibilidadeDropdownDTO {
    private Integer idRecurso;
    private String descricao;

    public RecursoAcessibilidadeDropdownDTO() {
    }

    public RecursoAcessibilidadeDropdownDTO(Integer idRecurso, String descricao) {
        this.idRecurso = idRecurso;
        this.descricao = descricao;
    }

    public static RecursoAcessibilidadeDropdownDTO from(RecursoAcessibilidade recurso) {
        return new RecursoAcessibilidadeDropdownDTO(
            recurso.getIdRecurso(),
            recurso.getDescricao()
        );
    }

    public Integer getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Integer idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}