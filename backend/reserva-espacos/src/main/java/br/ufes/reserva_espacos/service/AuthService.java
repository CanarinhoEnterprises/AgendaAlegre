package br.ufes.reserva_espacos.service;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.LoginDTO;
import br.ufes.reserva_espacos.dto.LoginResponseDTO;
import br.ufes.reserva_espacos.entity.Usuario;
import br.ufes.reserva_espacos.repositories.AdministradorRepository;
import br.ufes.reserva_espacos.repositories.SolicitanteRepository;
import br.ufes.reserva_espacos.repositories.UsuarioRepository;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final AdministradorRepository administradorRepository;
    private final SolicitanteRepository solicitanteRepository;

    public AuthService(
            UsuarioRepository usuarioRepository,
            AdministradorRepository administradorRepository,
            SolicitanteRepository solicitanteRepository) {

        this.usuarioRepository = usuarioRepository;
        this.administradorRepository = administradorRepository;
        this.solicitanteRepository = solicitanteRepository;
    }

    public LoginResponseDTO login(LoginDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getSenha().equals(dto.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        //Sabend se lel é administrador
        String tipo;

        if (administradorRepository.findByUsuario(usuario).isPresent()) {
            tipo = "ADMINISTRADOR";
        } else if (solicitanteRepository.findByUsuario(usuario).isPresent()) {
            tipo = "SOLICITANTE";
        } else {
            throw new RuntimeException("Tipo de usuário inválido");
        }

        return new LoginResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                tipo
        );
    }
}
