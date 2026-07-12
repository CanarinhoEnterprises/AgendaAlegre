package br.ufes.reserva_espacos.controller;

import java.util.List;
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

import br.ufes.reserva_espacos.dto.ocorrenciadto.CadastroOcorrenciaDTO;
import br.ufes.reserva_espacos.entity.Ocorrencia;
import br.ufes.reserva_espacos.service.OcorrenciaService;

@RestController
@RequestMapping("/ocorrencias")
public class OcorrenciaController {
    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @PostMapping
    public ResponseEntity<Ocorrencia> cadastrar(@RequestBody CadastroOcorrenciaDTO dto) {
        try {
            Ocorrencia ocorrencia = ocorrenciaService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ocorrencia);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Ocorrencia>> listar() {
        List<Ocorrencia> ocorrencias = ocorrenciaService.listar();
        return ResponseEntity.ok(ocorrencias);
    }

    @GetMapping("/espaco/{idEspaco}")
    public ResponseEntity<List<Ocorrencia>> listarPorEspaco(@PathVariable Integer idEspaco) {
        List<Ocorrencia> ocorrencias = ocorrenciaService.listarPorEspaco(idEspaco);
        return ResponseEntity.ok(ocorrencias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ocorrencia> consultar(@PathVariable Integer id) {
        Optional<Ocorrencia> ocorrencia = ocorrenciaService.consultar(id);
        if (ocorrencia.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ocorrencia.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ocorrencia> atualizar(@PathVariable Integer id, @RequestBody CadastroOcorrenciaDTO dto) {
        try {
            Ocorrencia ocorrencia = ocorrenciaService.atualizar(id, dto);
            return ResponseEntity.ok(ocorrencia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        try {
            ocorrenciaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
