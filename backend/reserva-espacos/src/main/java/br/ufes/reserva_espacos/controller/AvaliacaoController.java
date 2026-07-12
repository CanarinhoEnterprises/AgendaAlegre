package br.ufes.reserva_espacos.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufes.reserva_espacos.dto.avaliacaodto.CadastroAvaliacaoDTO;
import br.ufes.reserva_espacos.entity.Avaliacao;
import br.ufes.reserva_espacos.service.AvaliacaoService;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {
    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<Avaliacao> cadastrar(@RequestBody CadastroAvaliacaoDTO dto) {
        try {
            Avaliacao avaliacao = avaliacaoService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(avaliacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<Avaliacao> consultarPorReserva(@PathVariable Integer idReserva) {
        Optional<Avaliacao> avaliacao = avaliacaoService.consultarPorReserva(idReserva);
        if (avaliacao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avaliacao.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> consultar(@PathVariable Integer id) {
        Optional<Avaliacao> avaliacao = avaliacaoService.consultar(id);
        if (avaliacao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avaliacao.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizar(@PathVariable Integer id, @RequestBody CadastroAvaliacaoDTO dto) {
        try {
            Avaliacao avaliacao = avaliacaoService.atualizar(id, dto);
            return ResponseEntity.ok(avaliacao);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        try {
            avaliacaoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
