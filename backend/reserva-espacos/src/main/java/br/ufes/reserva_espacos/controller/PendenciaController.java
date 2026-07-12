package br.ufes.reserva_espacos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufes.reserva_espacos.dto.pendenciadto.CadastroPendenciaDTO;
import br.ufes.reserva_espacos.entity.Pendencia;
import br.ufes.reserva_espacos.enums.StatusPendencia;
import br.ufes.reserva_espacos.service.PendenciaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pendencias")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PendenciaController {
    private final PendenciaService pendenciaService;

    public PendenciaController(PendenciaService pendenciaService) {
        this.pendenciaService = pendenciaService;
    }

    @PostMapping
    public ResponseEntity<Pendencia> cadastrar(@RequestBody @Valid CadastroPendenciaDTO dto) {
        try {
            Pendencia pendencia = pendenciaService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(pendencia);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Pendencia>> listar() {
        List<Pendencia> pendencias = pendenciaService.listar();
        return ResponseEntity.ok(pendencias);
    }

    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<Pendencia>> listarPorReserva(@PathVariable Integer idReserva) {
        List<Pendencia> pendencias = pendenciaService.listarPorReserva(idReserva);
        return ResponseEntity.ok(pendencias);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pendencia>> listarPorStatus(@PathVariable StatusPendencia status) {
        List<Pendencia> pendencias = pendenciaService.listarPorStatus(status);
        return ResponseEntity.ok(pendencias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pendencia> consultar(@PathVariable Integer id) {
        Optional<Pendencia> pendencia = pendenciaService.consultar(id);
        if (pendencia.isPresent()) {
            return ResponseEntity.ok(pendencia.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pendencia> atualizar(@PathVariable Integer id,
            @RequestBody @Valid CadastroPendenciaDTO dto) {
        try {
            Pendencia pendencia = pendenciaService.atualizar(id, dto);
            return ResponseEntity.ok(pendencia);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        try {
            pendenciaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
