package br.ufes.reserva_espacos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.AnaliseReserva;

public interface AnaliseReservaRepository extends JpaRepository<AnaliseReserva, Integer> {
    Optional<AnaliseReserva> findByReserva_IdReserva(Integer idReserva);
}
