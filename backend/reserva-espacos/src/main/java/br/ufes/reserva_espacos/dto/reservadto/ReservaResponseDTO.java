package br.ufes.reserva_espacos.dto.reservadto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import br.ufes.reserva_espacos.entity.Reserva;
import br.ufes.reserva_espacos.enums.StatusReserva;

public class ReservaResponseDTO {
    private Integer idReserva;
    private Integer idSolicitante;
    private String nomeSolicitante;
    private Integer idEspaco;
    private String nomeEspaco;
    private LocalDate dtUso;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String finalidade;
    private Integer qtdPessoas;
    private LocalDateTime dtSolicitacao;
    private LocalDateTime dtCancelamento;
    private LocalDateTime dtConfirmacao;
    private StatusReserva status;

    public ReservaResponseDTO() {
    }

    public static ReservaResponseDTO fromEntity(Reserva reserva) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.idReserva = reserva.getIdReserva();
        dto.idSolicitante = reserva.getSolicitante() != null ? reserva.getSolicitante().getId() : null;
        dto.nomeSolicitante = reserva.getSolicitante() != null && reserva.getSolicitante().getUsuario() != null
                ? reserva.getSolicitante().getUsuario().getNome() : null;
        dto.idEspaco = reserva.getEspaco() != null ? reserva.getEspaco().getIdEspaco() : null;
        dto.nomeEspaco = reserva.getEspaco() != null ? reserva.getEspaco().getNome() : null;
        dto.dtUso = reserva.getDtUso();
        dto.horaInicio = reserva.getHoraInicio();
        dto.horaFim = reserva.getHoraFim();
        dto.finalidade = reserva.getFinalidade();
        dto.qtdPessoas = reserva.getQtdPessoas();
        dto.dtSolicitacao = reserva.getDtSolicitacao();
        dto.dtCancelamento = reserva.getDtCancelamento();
        dto.dtConfirmacao = reserva.getDtConfirmacao();
        dto.status = reserva.getStatus();
        return dto;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public Integer getIdSolicitante() {
        return idSolicitante;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public Integer getIdEspaco() {
        return idEspaco;
    }

    public String getNomeEspaco() {
        return nomeEspaco;
    }

    public LocalDate getDtUso() {
        return dtUso;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public Integer getQtdPessoas() {
        return qtdPessoas;
    }

    public LocalDateTime getDtSolicitacao() {
        return dtSolicitacao;
    }

    public LocalDateTime getDtCancelamento() {
        return dtCancelamento;
    }

    public LocalDateTime getDtConfirmacao() {
        return dtConfirmacao;
    }

    public StatusReserva getStatus() {
        return status;
    }
}
