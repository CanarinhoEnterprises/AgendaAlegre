package br.ufes.reserva_espacos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.Solicitante;
import br.ufes.reserva_espacos.entity.Usuario;

public interface SolicitanteRepository extends JpaRepository<Solicitante, Integer>{

    boolean existsByCpfCnpj(String cpfCnpj);
    
    Optional<Solicitante> findByUsuario(Usuario usuario);
}
