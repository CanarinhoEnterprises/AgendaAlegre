package br.ufes.reserva_espacos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.Espaco;
import br.ufes.reserva_espacos.enums.StatusEspaco;

public interface EspacoRepository extends JpaRepository<Espaco, Integer> {
	List<Espaco> findAllByOrderByNomeAsc();

	List<Espaco> findAllByStatusOrderByNomeAsc(StatusEspaco status);
}
