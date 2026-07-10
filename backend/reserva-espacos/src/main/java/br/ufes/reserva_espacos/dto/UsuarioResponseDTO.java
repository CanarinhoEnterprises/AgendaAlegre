package br.ufes.reserva_espacos.dto;

public class UsuarioResponseDTO {

    private Integer idUsuario;
    private String nome;
    private String telefone;
    private String email;

    public UsuarioResponseDTO() {

    }

    public UsuarioResponseDTO(Integer idUsuario, String nome, String telefone, String email) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }
}