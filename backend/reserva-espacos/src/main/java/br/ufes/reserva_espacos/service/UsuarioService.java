package br.ufes.reserva_espacos.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.usuariodto.CadastroUsuarioDTO;
import br.ufes.reserva_espacos.dto.usuariodto.UsuarioResponseDTO;
import br.ufes.reserva_espacos.entity.Administrador;
import br.ufes.reserva_espacos.entity.Solicitante;
import br.ufes.reserva_espacos.entity.Usuario;
import br.ufes.reserva_espacos.repositories.AdministradorRepository;
import br.ufes.reserva_espacos.repositories.SolicitanteRepository;
import br.ufes.reserva_espacos.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    
    private final UsuarioRepository usuarioRepository;
    private final SolicitanteRepository solicitanteRepository;
    private final AdministradorRepository administradorRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          SolicitanteRepository solicitanteRepository,
                          AdministradorRepository administradorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.solicitanteRepository = solicitanteRepository;
        this.administradorRepository = administradorRepository;
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(CadastroUsuarioDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        if (solicitanteRepository.existsByCpfCnpj(dto.getCpfCnpj())) {
            throw new RuntimeException("CPF/CNPJ já cadastrado.");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setTelefone(dto.getTelefone());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        usuario = usuarioRepository.save(usuario);


        //Todo usuario já é solicitante
        Solicitante solicitante = new Solicitante();

        solicitante.setUsuario(usuario);
        solicitante.setTipoPessoa(dto.getTipoPessoa());
        solicitante.setCpfCnpj(dto.getCpfCnpj());

        solicitanteRepository.save(solicitante);


        return UsuarioResponseDTO.from(usuario);
    }

    public List<UsuarioResponseDTO> listar(){
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::from)
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Integer id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        return UsuarioResponseDTO.from(usuario);
    }

    @Transactional
    public void excluir(Integer id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Optional<Solicitante> solicitante = solicitanteRepository.findByUsuario(usuario);
        solicitante.ifPresent(solicitanteRepository::delete);

        Optional<Administrador> administrador = administradorRepository.findByUsuario(usuario);
        administrador.ifPresent(administradorRepository::delete);

        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        usuarioRepository.deleteById(id);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Integer id, Usuario dados) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!usuario.getEmail().equals(dados.getEmail())
                && usuarioRepository.existsByEmail(dados.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        usuario.setNome(dados.getNome());
        usuario.setTelefone(dados.getTelefone());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(dados.getSenha());

        return UsuarioResponseDTO.from(usuarioRepository.save(usuario));
    }
}
