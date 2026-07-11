package br.ufes.reserva_espacos.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {

    public Usuario(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idusuario", nullable = false)
    private Integer idUsuario;
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }


    @Column(name="nome", nullable = false, length = 100)
    private String nome;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name="telefone", nullable = false, length = 20)
    private String telefone;

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Column(name="email", nullable = false, unique = true, length = 255)
    private String email;

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    @Column(name="senha", nullable = false, length = 255)
    private String senha;

    public String getSenha(){
        return senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }

}
