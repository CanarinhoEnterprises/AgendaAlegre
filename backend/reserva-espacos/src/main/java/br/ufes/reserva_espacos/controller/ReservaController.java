package br.ufes.reserva_espacos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufes.reserva_espacos.dto.reservadto.CadastroReservaDTO;
import br.ufes.reserva_espacos.dto.reservadto.CancelarReservaDTO;
import br.ufes.reserva_espacos.dto.reservadto.ReservaResponseDTO;
import br.ufes.reserva_espacos.entity.Reserva;
import br.ufes.reserva_espacos.enums.StatusReserva;
import br.ufes.reserva_espacos.service.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

	private final ReservaService reservaService;

	public ReservaController(ReservaService reservaService) {
		this.reservaService = reservaService;
	}

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody CadastroReservaDTO dto) {
		try {
			Reserva reserva = reservaService.cadastrar(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(ReservaResponseDTO.fromEntity(reserva));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("mensagem", e.getMessage()));
		}
	}

	@GetMapping
	public List<ReservaResponseDTO> listar() {
		return reservaService.listarDTO(reservaService.listar());
	}

	@GetMapping("/solicitante/{idSolicitante}")
	public List<ReservaResponseDTO> listarPorSolicitante(@PathVariable Integer idSolicitante) {
		return reservaService.listarDTO(reservaService.listarPorSolicitante(idSolicitante));
	}

	@GetMapping("/status/{status}")
	public List<ReservaResponseDTO> listarPorStatus(@PathVariable StatusReserva status) {
		return reservaService.listarDTO(reservaService.listarPorStatus(status));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
		try {
			return ResponseEntity.ok(ReservaResponseDTO.fromEntity(reservaService.buscarPorId(id)));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Integer id) {
		reservaService.excluir(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody CadastroReservaDTO dto) {
		try {
			Reserva reserva = reservaService.atualizar(id, dto);
			return ResponseEntity.ok(ReservaResponseDTO.fromEntity(reserva));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("mensagem", e.getMessage()));
		}
	}

	@PatchMapping("/{id}/cancelar")
	public ResponseEntity<?> cancelar(@PathVariable Integer id, @RequestBody CancelarReservaDTO dto) {
		try {
			Reserva reserva = reservaService.cancelar(id, dto);
			return ResponseEntity.ok(ReservaResponseDTO.fromEntity(reserva));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("mensagem", e.getMessage()));
		}
	}
}
