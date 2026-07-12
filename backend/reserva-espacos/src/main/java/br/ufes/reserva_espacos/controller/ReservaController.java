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

import br.ufes.reserva_espacos.dto.reservadto.CadastroReservaDTO;
import br.ufes.reserva_espacos.entity.Reserva;
import br.ufes.reserva_espacos.service.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

	private final ReservaService reservaService;

	public ReservaController(ReservaService reservaService) {
		this.reservaService = reservaService;
	}

	@PostMapping
	public Reserva cadastrar(@RequestBody CadastroReservaDTO dto) {
		return reservaService.cadastrar(dto);
	}

	@GetMapping
	public List<Reserva> listar() {
		return reservaService.listar();
	}

	@GetMapping("/solicitante/{idSolicitante}")
	public List<Reserva> listarPorSolicitante(@PathVariable Integer idSolicitante) {
		return reservaService.listarPorSolicitante(idSolicitante);
	}

	@GetMapping("/{id}")
	public Reserva buscarPorId(@PathVariable Integer id) {
		return reservaService.buscarPorId(id);
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Integer id) {
		reservaService.excluir(id);
	}

	@PutMapping("/{id}")
	public Reserva atualizar(@PathVariable Integer id, @RequestBody CadastroReservaDTO dto) {
		return reservaService.atualizar(id, dto);
	}
}
