package br.ufes.reserva_espacos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.ufes.reserva_espacos.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}