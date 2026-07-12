package br.ufes.reserva_espacos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipamento")
public class Equipamento {

    public Equipamento() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idequipamento", nullable = false)
    private Integer idEquipamento;

    public Integer getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Integer idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    @ManyToOne
    @JoinColumn(name = "idespaco", nullable = false)
    private Espaco espaco;

    public Espaco getEspaco() {
        return espaco;
    }

    public void setEspaco(Espaco espaco) {
        this.espaco = espaco;
    }

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name = "qtd", nullable = false)
    private Integer qtd;

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }
}
