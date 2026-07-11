package br.ufes.reserva_espacos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufes.reserva_espacos.dto.CidadeDropdownDTO;
import br.ufes.reserva_espacos.dto.CidadeRequestDTO;
import br.ufes.reserva_espacos.dto.CidadeResponseDTO;
import br.ufes.reserva_espacos.entity.Cidade;
import br.ufes.reserva_espacos.service.CidadeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/cidades")
public class CidadeController {
    private final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService){
        this.cidadeService = cidadeService;
    }

    @PostMapping
    public Cidade cadastrar(@RequestBody CidadeRequestDTO dto) {
        return cidadeService.cadastrar(dto);
    }

    @GetMapping
    public List<CidadeResponseDTO> listarUF_Nome() {
        return cidadeService.listarUF_Nome();
    }

    @GetMapping("/combo")
    public List<CidadeDropdownDTO> listarCombo() {
        return cidadeService.listarCombo();
    }

    @GetMapping("/{id}")
    public Cidade buscaPorId(@PathVariable Integer id) {
        return cidadeService.buscaPorId(id);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id){
        cidadeService.excluir(id);
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Integer id, @RequestBody Cidade dados) {
        return cidadeService.atualizar(id, dados);
    }
}
