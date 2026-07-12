package br.ufes.reserva_espacos.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import br.ufes.reserva_espacos.enums.StatusReserva;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva")
public class Reserva {

	public Reserva() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idreserva", nullable = false)
	private Integer idReserva;

	public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	@ManyToOne
	@JoinColumn(name = "idsolicitante", nullable = false)
	private Solicitante solicitante;

	public Solicitante getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Solicitante solicitante) {
		this.solicitante = solicitante;
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

	@Column(name = "horainicio", nullable = false)
	private LocalTime horaInicio;

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	@Column(name = "dtuso", nullable = false)
	private LocalDate dtUso;

	public LocalDate getDtUso() {
		return dtUso;
	}

	public void setDtUso(LocalDate dtUso) {
		this.dtUso = dtUso;
	}

	@Column(name = "horafim", nullable = false)
	private LocalTime horaFim;

	public LocalTime getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(LocalTime horaFim) {
		this.horaFim = horaFim;
	}

	@Column(name = "finalidade", nullable = false, length = 255)
	private String finalidade;

	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	@Column(name = "dtsolicitacao", nullable = false)
	private LocalDateTime dtSolicitacao;

	public LocalDateTime getDtSolicitacao() {
		return dtSolicitacao;
	}

	public void setDtSolicitacao(LocalDateTime dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}

	@Column(name = "dtcancelamento")
	private LocalDateTime dtCancelamento;

	public LocalDateTime getDtCancelamento() {
		return dtCancelamento;
	}

	public void setDtCancelamento(LocalDateTime dtCancelamento) {
		this.dtCancelamento = dtCancelamento;
	}

	@Column(name = "dtconfirmacao")
	private LocalDateTime dtConfirmacao;

	public LocalDateTime getDtConfirmacao() {
		return dtConfirmacao;
	}

	public void setDtConfirmacao(LocalDateTime dtConfirmacao) {
		this.dtConfirmacao = dtConfirmacao;
	}

	@Column(name = "qtdpessoas", nullable = false)
	private Integer qtdPessoas;

	public Integer getQtdPessoas() {
		return qtdPessoas;
	}

	public void setQtdPessoas(Integer qtdPessoas) {
		this.qtdPessoas = qtdPessoas;
	}

	@JdbcTypeCode(SqlTypes.NAMED_ENUM)
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, columnDefinition = "status_reserva")
	private StatusReserva status;

	public StatusReserva getStatus() {
		return status;
	}

	public void setStatus(StatusReserva status) {
		this.status = status;
	}
}
