package br.ufes.reserva_espacos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.ModeloTermo;

public interface ModeloTermoRepository extends JpaRepository<ModeloTermo, Integer> {
	List<ModeloTermo> findAllByOrderByVersaoDesc();
}
