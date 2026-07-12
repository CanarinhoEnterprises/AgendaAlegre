package br.ufes.reserva_espacos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.ufes.reserva_espacos.entity.Administrador;
import br.ufes.reserva_espacos.entity.Usuario;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {

    boolean existsByCpf(String cpf);

    Optional<Administrador> findByUsuario(Usuario usuario);
}
