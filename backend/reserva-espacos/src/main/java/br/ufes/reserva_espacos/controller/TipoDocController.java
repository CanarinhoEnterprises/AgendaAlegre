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

import br.ufes.reserva_espacos.dto.tipodocdto.CadastroTipoDocDTO;
import br.ufes.reserva_espacos.entity.TipoDoc;
import br.ufes.reserva_espacos.service.TipoDocService;

@RestController
@RequestMapping("/tipos-doc")
public class TipoDocController {

	private final TipoDocService tipoDocService;

	public TipoDocController(TipoDocService tipoDocService) {
		this.tipoDocService = tipoDocService;
	}

	@PostMapping
	public TipoDoc cadastrar(@RequestBody CadastroTipoDocDTO dto) {
		return tipoDocService.cadastrar(dto);
	}

	@GetMapping
	public List<TipoDoc> listar() {
		return tipoDocService.listar();
	}

	@GetMapping("/{id}")
	public TipoDoc buscarPorId(@PathVariable Integer id) {
		return tipoDocService.buscarPorId(id);
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Integer id) {
		tipoDocService.excluir(id);
	}

	@PutMapping("/{id}")
	public TipoDoc atualizar(@PathVariable Integer id, @RequestBody CadastroTipoDocDTO dto) {
		return tipoDocService.atualizar(id, dto);
	}
}
