package br.ufes.reserva_espacos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="cidade")
public class Cidade{
    public Cidade(){

    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idcidade", nullable = false)
    private Integer idCidade;
    
    public Integer getIdCidade() {
        return idCidade;
    }
    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    @Column(name="uf", nullable = false, length = 2)
    private String UF;

    public String getUF() {
        return UF;
    }
    public void setUF(String uF) {
        UF = uF;
    }

    @Column(name="nome", nullable = false, length = 100)
    private String nome;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}