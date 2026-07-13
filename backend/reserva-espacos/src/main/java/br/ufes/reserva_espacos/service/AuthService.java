package br.ufes.reserva_espacos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.LoginDTO;
import br.ufes.reserva_espacos.dto.LoginResponseDTO;
import br.ufes.reserva_espacos.entity.Usuario;
import br.ufes.reserva_espacos.repositories.AdministradorRepository;
import br.ufes.reserva_espacos.repositories.SolicitanteRepository;
import br.ufes.reserva_espacos.repositories.UsuarioRepository;
import br.ufes.reserva_espacos.security.JwtService;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final AdministradorRepository administradorRepository;
    private final SolicitanteRepository solicitanteRepository;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            AdministradorRepository administradorRepository,
            SolicitanteRepository solicitanteRepository,
        JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.administradorRepository = administradorRepository;
        this.solicitanteRepository = solicitanteRepository;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getSenha().equals(dto.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        //Sabend se lel é administrador
        String tipo;
        List<String> roles = new ArrayList();
        if (administradorRepository.findByUsuario(usuario).isPresent()) {
            roles.add("ADMINISTRADOR");
        }

        if (solicitanteRepository.findByUsuario(usuario).isPresent()) {
            roles.add("SOLICITANTE");
        }

        if (roles.isEmpty()) {
            throw new RuntimeException("Usuário sem perfil cadastrado.");
        }

        if (administradorRepository.findByUsuario(usuario).isPresent()) {
            tipo = "ADMINISTRADOR";
        } else if (solicitanteRepository.findByUsuario(usuario).isPresent()) {
            tipo = "SOLICITANTE";
        } else {
            throw new RuntimeException("Tipo de usuário inválido");
        }

        String token = jwtService.gerarToken(usuario.getEmail(),roles);

        return new LoginResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                tipo,
                token
        );
    }
}
