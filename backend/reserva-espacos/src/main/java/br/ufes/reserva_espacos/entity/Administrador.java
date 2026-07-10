package br.ufes.reserva_espacos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="administrador")
public class Administrador {
    @Id
    @Column(name = "idusuario", nullable = false)
    private Integer idAdministrador;

    public Integer getId() {
        return idAdministrador;
    }
    //Nao temps setId pois o id tem que existir em usuario


    @OneToOne
    @MapsId
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    @Column(name="cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
