package br.ufes.reserva_espacos.service;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.CadastroAdministradorDTO;
import br.ufes.reserva_espacos.entity.Administrador;
import br.ufes.reserva_espacos.entity.Usuario;
import br.ufes.reserva_espacos.repositories.AdministradorRepository;
import br.ufes.reserva_espacos.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final UsuarioRepository usuarioRepository;


    public AdministradorService(
            AdministradorRepository administradorRepository,
            UsuarioRepository usuarioRepository) {

        this.administradorRepository = administradorRepository;
        this.usuarioRepository = usuarioRepository;
    }

@Transactional
public Administrador cadastrar(CadastroAdministradorDTO dto) {

    Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> 
                new RuntimeException("Usuário não encontrado.")
            );


    Integer idUsuario = usuario.getIdUsuario();


    if (administradorRepository.existsById(idUsuario)) {
        throw new RuntimeException("Usuário já é administrador.");
    }


    if (administradorRepository.existsByCpf(dto.getCpf())) {
        throw new RuntimeException("CPF já cadastrado.");
    }


    Administrador administrador = new Administrador();

    administrador.setUsuario(usuario);
    administrador.setCpf(dto.getCpf());


    return administradorRepository.save(administrador);
}
}