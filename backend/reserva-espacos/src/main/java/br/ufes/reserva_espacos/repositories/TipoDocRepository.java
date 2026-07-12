package br.ufes.reserva_espacos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.TipoDoc;

public interface TipoDocRepository extends JpaRepository<TipoDoc, Integer> {
	List<TipoDoc> findAllByOrderByNomeAsc();
}
