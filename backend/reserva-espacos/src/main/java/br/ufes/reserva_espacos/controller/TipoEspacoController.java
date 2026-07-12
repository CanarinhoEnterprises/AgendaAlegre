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

import br.ufes.reserva_espacos.dto.tipoespacodto.CadastroTipoEspacoDTO;
import br.ufes.reserva_espacos.entity.TipoEspaco;
import br.ufes.reserva_espacos.service.TipoEspacoService;

@RestController
@RequestMapping("/tipos-espaco")
public class TipoEspacoController {

	private final TipoEspacoService tipoEspacoService;

	public TipoEspacoController(TipoEspacoService tipoEspacoService) {
		this.tipoEspacoService = tipoEspacoService;
	}

	@PostMapping
	public TipoEspaco cadastrar(@RequestBody CadastroTipoEspacoDTO dto) {
		return tipoEspacoService.cadastrar(dto);
	}

	@GetMapping
	public List<TipoEspaco> listar() {
		return tipoEspacoService.listar();
	}

	@GetMapping("/{id}")
	public TipoEspaco buscarPorId(@PathVariable Integer id) {
		return tipoEspacoService.buscarPorId(id);
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Integer id) {
		tipoEspacoService.excluir(id);
	}

	@PutMapping("/{id}")
	public TipoEspaco atualizar(@PathVariable Integer id, @RequestBody CadastroTipoEspacoDTO dto) {
		return tipoEspacoService.atualizar(id, dto);
	}
}
