package br.ufes.reserva_espacos.controller;

import java.util.Map;

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

import br.ufes.reserva_espacos.dto.termoaceitodto.CadastroTermoAceitoDTO;
import br.ufes.reserva_espacos.entity.TermoAceito;
import br.ufes.reserva_espacos.service.TermoAceitoService;

@RestController
@RequestMapping("/termos-aceito")
public class TermoAceitoController {
    private final TermoAceitoService termoAceitoService;

    public TermoAceitoController(TermoAceitoService termoAceitoService) {
        this.termoAceitoService = termoAceitoService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody CadastroTermoAceitoDTO dto) {
        try {
            TermoAceito termo = termoAceitoService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(termo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", e.getMessage()));
        }
    }

    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<TermoAceito> consultarPorReserva(@PathVariable Integer idReserva) {
        Optional<TermoAceito> termo = termoAceitoService.consultarPorReserva(idReserva);
        if (termo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(termo.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TermoAceito> consultar(@PathVariable Integer id) {
        Optional<TermoAceito> termo = termoAceitoService.consultar(id);
        if (termo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(termo.get());
    }

    @PutMapping("/{id}/aceitar")
    public ResponseEntity<?> aceitar(@PathVariable Integer id) {
        try {
            TermoAceito termo = termoAceitoService.aceitar(id);
            return ResponseEntity.ok(termo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        try {
            termoAceitoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", e.getMessage()));
        }
    }
}
