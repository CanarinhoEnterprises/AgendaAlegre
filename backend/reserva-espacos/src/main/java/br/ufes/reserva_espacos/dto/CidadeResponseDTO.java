package br.ufes.reserva_espacos.dto;

import br.ufes.reserva_espacos.entity.Cidade;

public class CidadeResponseDTO {
    private Integer idCidade;
    private String nome;
    private String uf;

    public CidadeResponseDTO(){

    }

    public CidadeResponseDTO(Integer idCidade, String nome, String uf){
        this.idCidade = idCidade;
        this.nome = nome;
        this.uf = uf;
    }

    public static CidadeResponseDTO from(Cidade cidade){
        return new CidadeResponseDTO(
            cidade.getIdCidade(),
            cidade.getNome(),
            cidade.getUF()
        );
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
