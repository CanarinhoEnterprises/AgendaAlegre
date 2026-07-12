package br.ufes.reserva_espacos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufes.reserva_espacos.dto.modelotermodto.CadastroModeloTermoDTO;
import br.ufes.reserva_espacos.entity.ModeloTermo;
import br.ufes.reserva_espacos.service.ModeloTermoService;

@RestController
@RequestMapping("/modelos-termo")
public class ModeloTermoController {

	private final ModeloTermoService modeloTermoService;

	public ModeloTermoController(ModeloTermoService modeloTermoService) {
		this.modeloTermoService = modeloTermoService;
	}

	@PostMapping
	public ModeloTermo cadastrar(@RequestBody CadastroModeloTermoDTO dto) {
		return modeloTermoService.cadastrar(dto);
	}

	@GetMapping
	public List<ModeloTermo> listar() {
		return modeloTermoService.listar();
	}

	@GetMapping("/{id}")
	public ModeloTermo buscarPorId(@PathVariable Integer id) {
		return modeloTermoService.buscarPorId(id);
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Integer id) {
		modeloTermoService.excluir(id);
	}

	@PutMapping("/{id}")
	public ModeloTermo atualizar(@PathVariable Integer id, @RequestBody CadastroModeloTermoDTO dto) {
		return modeloTermoService.atualizar(id, dto);
	}
}
