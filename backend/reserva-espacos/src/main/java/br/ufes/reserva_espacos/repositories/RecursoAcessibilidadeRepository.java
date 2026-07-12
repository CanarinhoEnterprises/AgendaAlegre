package br.ufes.reserva_espacos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.RecursoAcessibilidade;

public interface RecursoAcessibilidadeRepository extends JpaRepository<RecursoAcessibilidade, Integer> {
    boolean existsByDescricao(String descricao); // verificar duplicidade antes de cadastrar

    List<RecursoAcessibilidade> findAllByOrderByDescricaoAsc(); // lista os recursos em ordem alfabética
}