package br.ufes.reserva_espacos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.TermoAceito;

public interface TermoAceitoRepository extends JpaRepository<TermoAceito, Integer> {
    Optional<TermoAceito> findByReserva_IdReserva(Integer idReserva);
}
