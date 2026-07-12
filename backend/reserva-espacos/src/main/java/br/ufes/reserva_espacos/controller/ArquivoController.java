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

import br.ufes.reserva_espacos.dto.arquivodto.CadastroArquivoDTO;
import br.ufes.reserva_espacos.entity.Arquivo;
import br.ufes.reserva_espacos.service.ArquivoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/arquivos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ArquivoController {
    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @PostMapping
    public ResponseEntity<Arquivo> cadastrar(@RequestBody @Valid CadastroArquivoDTO dto) {
        try {
            Arquivo arquivo = arquivoService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(arquivo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Arquivo>> listar() {
        List<Arquivo> arquivos = arquivoService.listar();
        return ResponseEntity.ok(arquivos);
    }

    @GetMapping("/pendencia/{idPendencia}")
    public ResponseEntity<List<Arquivo>> listarPorPendencia(@PathVariable Integer idPendencia) {
        List<Arquivo> arquivos = arquivoService.listarPorPendencia(idPendencia);
        return ResponseEntity.ok(arquivos);
    }

    @GetMapping("/tipodoc/{idTipoDoc}")
    public ResponseEntity<List<Arquivo>> listarPorTipoDoc(@PathVariable Integer idTipoDoc) {
        List<Arquivo> arquivos = arquivoService.listarPorTipoDoc(idTipoDoc);
        return ResponseEntity.ok(arquivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arquivo> consultar(@PathVariable Integer id) {
        Optional<Arquivo> arquivo = arquivoService.consultar(id);
        if (arquivo.isPresent()) {
            return ResponseEntity.ok(arquivo.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Arquivo> atualizar(@PathVariable Integer id,
            @RequestBody @Valid CadastroArquivoDTO dto) {
        try {
            Arquivo arquivo = arquivoService.atualizar(id, dto);
            return ResponseEntity.ok(arquivo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        try {
            arquivoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
