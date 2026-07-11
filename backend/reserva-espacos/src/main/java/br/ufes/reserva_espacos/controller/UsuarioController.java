package br.ufes.reserva_espacos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufes.reserva_espacos.dto.CadastroUsuarioDTO;
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
    public Usuario cadastrar(@Valid @RequestBody CadastroUsuarioDTO dto) {
        return usuarioService.cadastrar(dto);
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }


    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }


    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        usuarioService.excluir(id);
    }

}
