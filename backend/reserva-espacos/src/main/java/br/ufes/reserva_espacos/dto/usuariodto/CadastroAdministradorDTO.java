package br.ufes.reserva_espacos.dto.usuariodto;

public class CadastroAdministradorDTO {
    private String email;
    private String cpf;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
