package br.ufes.reserva_espacos.dto.enderecodto;

public class EnderecoRequestDTO {
    private Integer idCidade;
    private String logradouro;
    private String bairro;
    private String cep;
    private Integer num;
    private String comp;
    private String referencia;

    public EnderecoRequestDTO() {
    }

    public EnderecoRequestDTO(Integer idCidade, String logradouro, String bairro, String cep, Integer num, String comp, String referencia) {
        this.idCidade = idCidade;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cep = cep;
        this.num = num;
        this.comp = comp;
        this.referencia = referencia;
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}