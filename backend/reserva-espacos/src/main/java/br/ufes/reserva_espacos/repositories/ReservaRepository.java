package br.ufes.reserva_espacos.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.Reserva;
import br.ufes.reserva_espacos.enums.StatusReserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findAllByOrderByDtSolicitacaoDesc();

    List<Reserva> findBySolicitante_IdSolicitanteOrderByDtSolicitacaoDesc(Integer idSolicitante);

    boolean existsByEspaco_IdEspacoAndDtUsoAndStatusAndHoraInicioLessThanAndHoraFimGreaterThan(
	    Integer idEspaco,
	    LocalDate dtUso,
	    StatusReserva status,
	    LocalTime horaFim,
	    LocalTime horaInicio);

    boolean existsByEspaco_IdEspacoAndDtUsoAndStatusAndHoraInicioLessThanAndHoraFimGreaterThanAndIdReservaNot(
	    Integer idEspaco,
	    LocalDate dtUso,
	    StatusReserva status,
	    LocalTime horaFim,
	    LocalTime horaInicio,
	    Integer idReserva);
}
