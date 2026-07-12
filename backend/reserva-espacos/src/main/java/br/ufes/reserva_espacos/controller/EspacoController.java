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

import br.ufes.reserva_espacos.dto.espacodto.CadastroEspacoDTO;
import br.ufes.reserva_espacos.entity.Espaco;
import br.ufes.reserva_espacos.service.EspacoService;

@RestController
@RequestMapping("/espacos")
public class EspacoController {

	private final EspacoService espacoService;

	public EspacoController(EspacoService espacoService) {
		this.espacoService = espacoService;
	}

	@PostMapping
	public Espaco cadastrar(@RequestBody CadastroEspacoDTO dto) {
		return espacoService.cadastrar(dto);
	}

	@GetMapping
	public List<Espaco> listar() {
		return espacoService.listar();
	}

	@GetMapping("/{id}")
	public Espaco buscarPorId(@PathVariable Integer id) {
		return espacoService.buscarPorId(id);
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Integer id) {
		espacoService.excluir(id);
	}

	@PutMapping("/{id}")
	public Espaco atualizar(@PathVariable Integer id, @RequestBody CadastroEspacoDTO dto) {
		return espacoService.atualizar(id, dto);
	}
}
