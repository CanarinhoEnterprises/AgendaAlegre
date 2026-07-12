package br.ufes.reserva_espacos.dto.cidadedto;

public class CidadeRequestDTO {
    private String nome;
    private String uf;

    public CidadeRequestDTO(){
        
    }

    public CidadeRequestDTO(String nome, String uf){
        this.nome = nome;
        this.uf = uf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUF() {
        return uf;
    }

    public void setUF(String uf) {
        this.uf = uf;
    }
}
