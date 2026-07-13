package br.ufes.reserva_espacos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufes.reserva_espacos.dto.usuariodto.CadastroUsuarioDTO;
import br.ufes.reserva_espacos.dto.usuariodto.UsuarioResponseDTO;
import br.ufes.reserva_espacos.entity.Usuario;
import br.ufes.reserva_espacos.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

     @PostMapping
    public UsuarioResponseDTO cadastrar(@Valid @RequestBody CadastroUsuarioDTO dto) {
        return usuarioService.cadastrar(dto);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return usuarioService.listar();
    }


    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }


    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        usuarioService.excluir(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizar(@PathVariable Integer id, @RequestBody Usuario dados) {
        return usuarioService.atualizar(id, dados);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {

    return ResponseEntity.ok(
        Map.of(
            "authenticated", authentication.isAuthenticated(),
            "principal", authentication.getName(),
            "authorities", authentication.getAuthorities()
        )
    );
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<String> somenteAdmin() {

        return ResponseEntity.ok(
            "Acesso permitido para administrador!"
        );
    }
}
