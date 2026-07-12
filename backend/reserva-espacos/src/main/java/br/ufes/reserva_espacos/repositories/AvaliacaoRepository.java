package br.ufes.reserva_espacos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    Optional<Avaliacao> findByReserva_IdReserva(Integer idReserva);
}
