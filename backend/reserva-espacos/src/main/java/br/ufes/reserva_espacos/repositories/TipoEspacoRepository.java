package br.ufes.reserva_espacos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.TipoEspaco;

public interface TipoEspacoRepository extends JpaRepository<TipoEspaco, Integer> {
	List<TipoEspaco> findAllByOrderByNomeAsc();
}
