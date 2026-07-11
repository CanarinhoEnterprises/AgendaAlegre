package br.ufes.reserva_espacos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    boolean existsByNomeAndUF(String nome, String uf); //verificar duplicidade antes de cadastrar

    List<Cidade> findAllByOrderByUFAscNomeAsc(); //lista todas as cidades pelo estado
}
