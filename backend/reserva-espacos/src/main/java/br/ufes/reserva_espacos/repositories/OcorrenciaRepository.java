package br.ufes.reserva_espacos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.Ocorrencia;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Integer> {
    List<Ocorrencia> findByEspaco_IdEspacoOrderByDtRegistroDesc(Integer idEspaco);

    List<Ocorrencia> findAllByOrderByDtRegistroDesc();
}
