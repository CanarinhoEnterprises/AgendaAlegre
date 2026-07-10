package br.ufes.reserva_espacos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.ufes.reserva_espacos.entity.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {

    boolean existsByCpf(String cpf);
}
