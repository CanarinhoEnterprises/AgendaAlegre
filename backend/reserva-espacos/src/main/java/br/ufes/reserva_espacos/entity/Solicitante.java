package br.ufes.reserva_espacos.entity;

import br.ufes.reserva_espacos.enums.TipoPessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="solicitante")
public class Solicitante {
    
    @Id
    @Column(name="idusuario", nullable=false)
    private Integer idSolicitante;

    public Integer getId(){
        return idSolicitante;
    }


    @OneToOne
    @MapsId
    @JoinColumn(name="idusuario")
    private Usuario usuario;

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }


    @Enumerated(EnumType.STRING)
    @Column(name="tipopessoa", nullable = false, columnDefinition = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    public TipoPessoa getTipoPessoa(){
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa){
        this.tipoPessoa = tipoPessoa;
    }


    @Column(name = "cpfcnpj", nullable = false, unique = true, length = 14)
    private String cpfCnpj;

    public String getCpfCnpj(){
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj){
        this.cpfCnpj = cpfCnpj;
    }
    
}
