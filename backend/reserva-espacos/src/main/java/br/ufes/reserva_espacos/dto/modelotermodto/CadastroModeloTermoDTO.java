package br.ufes.reserva_espacos.dto.modelotermodto;

public class CadastroModeloTermoDTO {
	private Integer versao;
	private String titulo;
	private String texto;
	private String descricao;
	private Boolean ativo;

	public CadastroModeloTermoDTO() {
	}

	public CadastroModeloTermoDTO(Integer versao, String titulo, String texto, String descricao, Boolean ativo) {
		this.versao = versao;
		this.titulo = titulo;
		this.texto = texto;
		this.descricao = descricao;
		this.ativo = ativo;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
