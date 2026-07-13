package br.ufes.reserva_espacos.dto.reservadto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CadastroReservaDTO {
	private Integer idSolicitante;
	private Integer idEspaco;
	private LocalTime horaInicio;
	private LocalDate dtUso;
	private LocalTime horaFim;
	private String finalidade;
	private Integer qtdPessoas;

	public CadastroReservaDTO() {
	}

	public CadastroReservaDTO(Integer idSolicitante, Integer idEspaco, LocalTime horaInicio, LocalDate dtUso, LocalTime horaFim, String finalidade, Integer qtdPessoas) {
		this.idSolicitante = idSolicitante;
		this.idEspaco = idEspaco;
		this.horaInicio = horaInicio;
		this.dtUso = dtUso;
		this.horaFim = horaFim;
		this.finalidade = finalidade;
		this.qtdPessoas = qtdPessoas;
	}

	public Integer getIdSolicitante() {
		return idSolicitante;
	}

	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public Integer getIdEspaco() {
		return idEspaco;
	}

	public void setIdEspaco(Integer idEspaco) {
		this.idEspaco = idEspaco;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalDate getDtUso() {
		return dtUso;
	}

	public void setDtUso(LocalDate dtUso) {
		this.dtUso = dtUso;
	}

	public LocalTime getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(LocalTime horaFim) {
		this.horaFim = horaFim;
	}

	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	public Integer getQtdPessoas() {
		return qtdPessoas;
	}

	public void setQtdPessoas(Integer qtdPessoas) {
		this.qtdPessoas = qtdPessoas;
	}
}
