package br.ufes.reserva_espacos.dto;

public class LoginResponseDTO {
    private Integer id;
    private String nome;
    private String email;
    private String tipo;

    public LoginResponseDTO() {}

    public LoginResponseDTO(Integer id, String nome, String email, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    public Integer getId(){
        return id;
    }

    public String getNome() {
        return nome;
    }
    

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
