package br.ufes.reserva_espacos.dto.espacodto;

import java.util.List;

import br.ufes.reserva_espacos.enums.StatusEspaco;

public class CadastroEspacoDTO {
	private Integer idEndereco;
	private Integer idTipoEspaco;
	private Integer capacidade;
	private String nome;
	private String descricao;
	private String urlCapa;
	private StatusEspaco status;
	private List<Integer> idsRecursosAcessibilidade;

	public CadastroEspacoDTO() {
	}

	public CadastroEspacoDTO(Integer idEndereco, Integer idTipoEspaco, Integer capacidade, String nome, String descricao, String urlCapa, StatusEspaco status, List<Integer> idsRecursosAcessibilidade) {
		this.idEndereco = idEndereco;
		this.idTipoEspaco = idTipoEspaco;
		this.capacidade = capacidade;
		this.nome = nome;
		this.descricao = descricao;
		this.urlCapa = urlCapa;
		this.status = status;
		this.idsRecursosAcessibilidade = idsRecursosAcessibilidade;
	}

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public Integer getIdTipoEspaco() {
		return idTipoEspaco;
	}

	public void setIdTipoEspaco(Integer idTipoEspaco) {
		this.idTipoEspaco = idTipoEspaco;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrlCapa() {
		return urlCapa;
	}

	public void setUrlCapa(String urlCapa) {
		this.urlCapa = urlCapa;
	}

	public StatusEspaco getStatus() {
		return status;
	}

	public void setStatus(StatusEspaco status) {
		this.status = status;
	}

	public List<Integer> getIdsRecursosAcessibilidade() {
		return idsRecursosAcessibilidade;
	}

	public void setIdsRecursosAcessibilidade(List<Integer> idsRecursosAcessibilidade) {
		this.idsRecursosAcessibilidade = idsRecursosAcessibilidade;
	}
}
