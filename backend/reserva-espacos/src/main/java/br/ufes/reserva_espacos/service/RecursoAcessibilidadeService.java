package br.ufes.reserva_espacos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.recurscoacessibilidadedto.RecursoAcessibilidadeDropdownDTO;
import br.ufes.reserva_espacos.dto.recurscoacessibilidadedto.RecursoAcessibilidadeRequestDTO;
import br.ufes.reserva_espacos.dto.recurscoacessibilidadedto.RecursoAcessibilidadeResponseDTO;
import br.ufes.reserva_espacos.entity.RecursoAcessibilidade;
import br.ufes.reserva_espacos.repositories.RecursoAcessibilidadeRepository;
import jakarta.transaction.Transactional;

@Service
public class RecursoAcessibilidadeService {
    private final RecursoAcessibilidadeRepository recursoRepository;
    
    public RecursoAcessibilidadeService(RecursoAcessibilidadeRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    @Transactional
    public RecursoAcessibilidade cadastrar(RecursoAcessibilidadeRequestDTO dto) {
        if (recursoRepository.existsByDescricao(dto.getDescricao())) {
            throw new RuntimeException("Recurso de acessibilidade já cadastrado.");
        }

        RecursoAcessibilidade recurso = new RecursoAcessibilidade();
        recurso.setDescricao(dto.getDescricao());

        return recursoRepository.save(recurso);
    }

    public List<RecursoAcessibilidadeResponseDTO> listarTodos() {
        return recursoRepository.findAllByOrderByDescricaoAsc()
            .stream()
            .map(RecursoAcessibilidadeResponseDTO::from)
            .toList();
    }

    public List<RecursoAcessibilidadeDropdownDTO> listarCombo() {
        return recursoRepository.findAllByOrderByDescricaoAsc()
            .stream()
            .map(RecursoAcessibilidadeDropdownDTO::from)
            .toList();
    }

    public RecursoAcessibilidade buscaPorId(Integer id) {
        return recursoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recurso de acessibilidade não encontrado."));
    }

    public void excluir(Integer id) {
        if (!recursoRepository.existsById(id)) {
            throw new RuntimeException("Recurso de acessibilidade não encontrado.");
        }
        recursoRepository.deleteById(id);
    }

    @Transactional
    public RecursoAcessibilidade atualizar(Integer id, RecursoAcessibilidade dados) {
        RecursoAcessibilidade recurso = buscaPorId(id);

        if (!recurso.getDescricao().equals(dados.getDescricao()) && recursoRepository.existsByDescricao(dados.getDescricao())) {
            throw new RuntimeException("Recurso de acessibilidade já cadastrado com essa descrição.");
        }

        recurso.setDescricao(dados.getDescricao());

        return recursoRepository.save(recurso);
    }
}