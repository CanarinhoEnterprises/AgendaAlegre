package br.ufes.reserva_espacos.dto.cidadedto;

import br.ufes.reserva_espacos.entity.Cidade;

public class CidadeDropdownDTO {
    private String comboNomeUF;

    public CidadeDropdownDTO(){

    }

    public CidadeDropdownDTO(String comboNomeUF){
        
        this.comboNomeUF = comboNomeUF;
    }

    public static CidadeDropdownDTO from(Cidade cidade) {
        return new CidadeDropdownDTO(
            cidade.getNome() + " | " + cidade.getUF()
        );
    } 

    public String getComboNomeUF() {
        return comboNomeUF;
    }

    public void setComboNomeUF(String comboNomeUF) {
        this.comboNomeUF = comboNomeUF;
    }
}
