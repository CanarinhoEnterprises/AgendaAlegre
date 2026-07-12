package br.ufes.reserva_espacos.dto.equipamentodto;

public class CadastroEquipamentoDTO {
    private Integer idEspaco;
    private String nome;
    private String descricao;
    private Integer qtd;

    public CadastroEquipamentoDTO() {
    }

    public CadastroEquipamentoDTO(Integer idEspaco, String nome, String descricao, Integer qtd) {
        this.idEspaco = idEspaco;
        this.nome = nome;
        this.descricao = descricao;
        this.qtd = qtd;
    }

    public Integer getIdEspaco() {
        return idEspaco;
    }

    public void setIdEspaco(Integer idEspaco) {
        this.idEspaco = idEspaco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }
}
