package br.ufes.reserva_espacos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufes.reserva_espacos.dto.enderecodto.EnderecoRequestDTO;
import br.ufes.reserva_espacos.dto.enderecodto.EnderecoResponseDTO;
import br.ufes.reserva_espacos.entity.Endereco;
import br.ufes.reserva_espacos.service.EnderecoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    public Endereco cadastrar(@RequestBody EnderecoRequestDTO dto) {
        return enderecoService.cadastrar(dto);
    }

    @GetMapping
    public List<EnderecoResponseDTO> listarTodos() {
        return enderecoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Endereco buscaPorId(@PathVariable Integer id) {
        return enderecoService.buscaPorId(id);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        enderecoService.excluir(id);
    }

    @PutMapping("/{id}")
    public Endereco atualizar(@PathVariable Integer id, @RequestBody EnderecoRequestDTO dados) {
        return enderecoService.atualizar(id, dados);
    }
}