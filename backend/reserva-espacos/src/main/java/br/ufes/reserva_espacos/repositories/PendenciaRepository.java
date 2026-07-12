package br.ufes.reserva_espacos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.Pendencia;
import br.ufes.reserva_espacos.enums.StatusPendencia;

public interface PendenciaRepository extends JpaRepository<Pendencia, Integer> {
    List<Pendencia> findByReserva_IdReservaOrderByDtCriacaoDesc(Integer idReserva);
    
    List<Pendencia> findByStatusOrderByDtCriacaoDesc(StatusPendencia status);
    
    List<Pendencia> findAllByOrderByDtCriacaoDesc();
}
