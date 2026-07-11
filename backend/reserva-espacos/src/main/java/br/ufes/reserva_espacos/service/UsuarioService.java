package br.ufes.reserva_espacos.service;


import java.util.List;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.CadastroUsuarioDTO;
import br.ufes.reserva_espacos.entity.Solicitante;
import br.ufes.reserva_espacos.entity.Usuario;
import br.ufes.reserva_espacos.repositories.SolicitanteRepository;
import br.ufes.reserva_espacos.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    
    private final UsuarioRepository usuarioRepository;
    private final SolicitanteRepository solicitanteRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          SolicitanteRepository solicitanteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.solicitanteRepository = solicitanteRepository;
    }

    @Transactional
    public Usuario cadastrar(CadastroUsuarioDTO dto) {

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


        return usuario;
    }

    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id) {

        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public void excluir(Integer id) {

        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        usuarioRepository.deleteById(id);
    }

        public Usuario atualizar(Integer id, Usuario dados) {

        Usuario usuario = buscarPorId(id);

        if (!usuario.getEmail().equals(dados.getEmail())
                && usuarioRepository.existsByEmail(dados.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        usuario.setNome(dados.getNome());
        usuario.setTelefone(dados.getTelefone());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(dados.getSenha());

        return usuarioRepository.save(usuario);
    }

}
