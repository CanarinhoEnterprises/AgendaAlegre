package br.ufes.reserva_espacos.dto.enderecodto;

import br.ufes.reserva_espacos.entity.Endereco;

public class EnderecoResponseDTO {
    private Integer idEndereco;
    private Integer idCidade;
    private String nomeCidade;
    private String ufCidade;
    private String logradouro;
    private String bairro;
    private String cep;
    private Integer num;
    private String comp;
    private String referencia;

    public EnderecoResponseDTO() {
    }

    public EnderecoResponseDTO(Integer idEndereco, Integer idCidade, String nomeCidade, String ufCidade, String logradouro, String bairro, String cep, Integer num, String comp, String referencia) {
        this.idEndereco = idEndereco;
        this.idCidade = idCidade;
        this.nomeCidade = nomeCidade;
        this.ufCidade = ufCidade;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cep = cep;
        this.num = num;
        this.comp = comp;
        this.referencia = referencia;
    }

    public static EnderecoResponseDTO from(Endereco endereco) {
        return new EnderecoResponseDTO(
            endereco.getIdEndereco(),
            endereco.getCidade().getIdCidade(),
            endereco.getCidade().getNome(),
            endereco.getCidade().getUF(),
            endereco.getLogradouro(),
            endereco.getBairro(),
            endereco.getCep(),
            endereco.getNum(),
            endereco.getComp(),
            endereco.getReferencia()
        );
    }

    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getUfCidade() {
        return ufCidade;
    }

    public void setUfCidade(String ufCidade) {
        this.ufCidade = ufCidade;
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