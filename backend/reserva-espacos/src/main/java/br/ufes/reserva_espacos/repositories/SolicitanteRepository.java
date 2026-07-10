package br.ufes.reserva_espacos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.Solicitante;

public interface SolicitanteRepository extends JpaRepository<Solicitante, Integer>{

    boolean existsByCpfCnpj(String cpfCnpj);     
}
