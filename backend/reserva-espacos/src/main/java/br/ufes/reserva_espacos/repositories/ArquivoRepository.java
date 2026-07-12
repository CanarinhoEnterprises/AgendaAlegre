package br.ufes.reserva_espacos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufes.reserva_espacos.entity.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
    List<Arquivo> findByPendencia_IdPendenciaOrderByDtUploadDesc(Integer idPendencia);
    
    List<Arquivo> findByTipoDoc_IdTipoDocOrderByDtUploadDesc(Integer idTipoDoc);
}
