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

import br.ufes.reserva_espacos.dto.equipamentodto.CadastroEquipamentoDTO;
import br.ufes.reserva_espacos.entity.Equipamento;
import br.ufes.reserva_espacos.service.EquipamentoService;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {
    private final EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @PostMapping
    public ResponseEntity<Equipamento> cadastrar(@RequestBody CadastroEquipamentoDTO dto) {
        try {
            Equipamento equipamento = equipamentoService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(equipamento);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Equipamento>> listar() {
        List<Equipamento> equipamentos = equipamentoService.listar();
        return ResponseEntity.ok(equipamentos);
    }

    @GetMapping("/espaco/{idEspaco}")
    public ResponseEntity<List<Equipamento>> listarPorEspaco(@PathVariable Integer idEspaco) {
        List<Equipamento> equipamentos = equipamentoService.listarPorEspaco(idEspaco);
        return ResponseEntity.ok(equipamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> consultar(@PathVariable Integer id) {
        Optional<Equipamento> equipamento = equipamentoService.consultar(id);
        if (equipamento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipamento.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipamento> atualizar(@PathVariable Integer id, @RequestBody CadastroEquipamentoDTO dto) {
        try {
            Equipamento equipamento = equipamentoService.atualizar(id, dto);
            return ResponseEntity.ok(equipamento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        try {
            equipamentoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
