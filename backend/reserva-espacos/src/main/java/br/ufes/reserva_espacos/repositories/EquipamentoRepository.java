package br.ufes.reserva_espacos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.Equipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {
    List<Equipamento> findByEspaco_IdEspacoOrderByNomeAsc(Integer idEspaco);
}
