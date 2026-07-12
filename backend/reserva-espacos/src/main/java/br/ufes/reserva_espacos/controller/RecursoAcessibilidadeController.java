package br.ufes.reserva_espacos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufes.reserva_espacos.dto.recurscoacessibilidadedto.RecursoAcessibilidadeDropdownDTO;
import br.ufes.reserva_espacos.dto.recurscoacessibilidadedto.RecursoAcessibilidadeRequestDTO;
import br.ufes.reserva_espacos.dto.recurscoacessibilidadedto.RecursoAcessibilidadeResponseDTO;
import br.ufes.reserva_espacos.entity.RecursoAcessibilidade;
import br.ufes.reserva_espacos.service.RecursoAcessibilidadeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/recursos-acessibilidade")
public class RecursoAcessibilidadeController {
    private final RecursoAcessibilidadeService recursoService;

    public RecursoAcessibilidadeController(RecursoAcessibilidadeService recursoService) {
        this.recursoService = recursoService;
    }

    @PostMapping
    public RecursoAcessibilidade cadastrar(@RequestBody RecursoAcessibilidadeRequestDTO dto) {
        return recursoService.cadastrar(dto);
    }

    @GetMapping
    public List<RecursoAcessibilidadeResponseDTO> listarTodos() {
        return recursoService.listarTodos();
    }

    @GetMapping("/combo")
    public List<RecursoAcessibilidadeDropdownDTO> listarCombo() {
        return recursoService.listarCombo();
    }

    @GetMapping("/{id}")
    public RecursoAcessibilidade buscaPorId(@PathVariable Integer id) {
        return recursoService.buscaPorId(id);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        recursoService.excluir(id);
    }

    @PutMapping("/{id}")
    public RecursoAcessibilidade atualizar(@PathVariable Integer id, @RequestBody RecursoAcessibilidade dados) {
        return recursoService.atualizar(id, dados);
    }
}