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

import br.ufes.reserva_espacos.dto.analisereservadto.CadastroAnaliseReservaDTO;
import br.ufes.reserva_espacos.entity.AnaliseReserva;
import br.ufes.reserva_espacos.service.AnaliseReservaService;

@RestController
@RequestMapping("/analises-reserva")
public class AnaliseReservaController {
    private final AnaliseReservaService analiseReservaService;

    public AnaliseReservaController(AnaliseReservaService analiseReservaService) {
        this.analiseReservaService = analiseReservaService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody CadastroAnaliseReservaDTO dto) {
        try {
            AnaliseReserva analise = analiseReservaService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(analise);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", e.getMessage()));
        }
    }

    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<AnaliseReserva> consultarPorReserva(@PathVariable Integer idReserva) {
        Optional<AnaliseReserva> analise = analiseReservaService.consultarPorReserva(idReserva);
        if (analise.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(analise.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnaliseReserva> consultar(@PathVariable Integer id) {
        Optional<AnaliseReserva> analise = analiseReservaService.consultar(id);
        if (analise.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(analise.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody CadastroAnaliseReservaDTO dto) {
        try {
            AnaliseReserva analise = analiseReservaService.atualizar(id, dto);
            return ResponseEntity.ok(analise);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        try {
            analiseReservaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", e.getMessage()));
        }
    }
}
