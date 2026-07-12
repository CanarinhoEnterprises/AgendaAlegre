package br.ufes.reserva_espacos.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import br.ufes.reserva_espacos.enums.StatusEspaco;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "espaco")
public class Espaco {

	public Espaco() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idespaco", nullable = false)
	private Integer idEspaco;

	public Integer getIdEspaco() {
		return idEspaco;
	}

	public void setIdEspaco(Integer idEspaco) {
		this.idEspaco = idEspaco;
	}

	@ManyToOne
	@JoinColumn(name = "idendereco", nullable = false)
	private Endereco endereco;

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@ManyToOne
	@JoinColumn(name = "idtipoespaco", nullable = false)
	private TipoEspaco tipoEspaco;

	public TipoEspaco getTipoEspaco() {
		return tipoEspaco;
	}

	public void setTipoEspaco(TipoEspaco tipoEspaco) {
		this.tipoEspaco = tipoEspaco;
	}

	@Column(name = "capacidade", nullable = false)
	private Integer capacidade;

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
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

	@Column(name = "urlcapa", length = 500)
	private String urlCapa;

	public String getUrlCapa() {
		return urlCapa;
	}

	public void setUrlCapa(String urlCapa) {
		this.urlCapa = urlCapa;
	}

	@JdbcTypeCode(SqlTypes.NAMED_ENUM)
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, columnDefinition = "status_espaco")
	private StatusEspaco status;

	public StatusEspaco getStatus() {
		return status;
	}

	public void setStatus(StatusEspaco status) {
		this.status = status;
	}

	@ManyToMany
	@JoinTable(
		name = "espaco_recurso",
		joinColumns = @JoinColumn(name = "idespaco"),
		inverseJoinColumns = @JoinColumn(name = "idrecurso")
	)
	private List<RecursoAcessibilidade> recursosAcessibilidade = new ArrayList<>();

	public List<RecursoAcessibilidade> getRecursosAcessibilidade() {
		return recursosAcessibilidade;
	}

	public void setRecursosAcessibilidade(List<RecursoAcessibilidade> recursosAcessibilidade) {
		this.recursosAcessibilidade = recursosAcessibilidade;
	}
}
