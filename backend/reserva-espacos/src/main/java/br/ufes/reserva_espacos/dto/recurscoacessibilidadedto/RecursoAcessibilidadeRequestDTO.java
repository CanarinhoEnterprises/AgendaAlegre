package br.ufes.reserva_espacos.dto.recurscoacessibilidadedto;

public class RecursoAcessibilidadeRequestDTO {
    private String descricao;

    public RecursoAcessibilidadeRequestDTO() {
    }

    public RecursoAcessibilidadeRequestDTO(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}