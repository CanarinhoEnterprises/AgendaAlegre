package br.ufes.reserva_espacos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipoespaco")
public class TipoEspaco {

	public TipoEspaco() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtipoespaco", nullable = false)
	private Integer idTipoEspaco;

	public Integer getIdTipoEspaco() {
		return idTipoEspaco;
	}

	public void setIdTipoEspaco(Integer idTipoEspaco) {
		this.idTipoEspaco = idTipoEspaco;
	}

	@Column(name = "nome", nullable = false, length = 100)
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "descricao", columnDefinition = "TEXT")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
