package br.ufes.reserva_espacos.dto;

import br.ufes.reserva_espacos.entity.Cidade;

public class CidadeDropdownDTO {
    private Integer idCidade;
    private String comboNomeUF;

    public CidadeDropdownDTO(){

    }

    public CidadeDropdownDTO(Integer idCidade, String comboNomeUF){
        this.idCidade = idCidade;
        this.comboNomeUF = comboNomeUF;
    }

    public static CidadeDropdownDTO from(Cidade cidade) {
        return new CidadeDropdownDTO(
            cidade.getIdCidade(), cidade.getNome() + " | " + cidade.getUF()
        );
    } 

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public String getcomboNomeUF() {
        return comboNomeUF;
    }

    public void setcomboNomeUF(String comboNomeUF) {
        this.comboNomeUF = comboNomeUF;
    }
}
