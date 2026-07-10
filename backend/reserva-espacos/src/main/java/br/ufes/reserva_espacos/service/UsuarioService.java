package br.ufes.reserva_espacos.service;


import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.repositories.SolicitanteRepository;
import br.ufes.reserva_espacos.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
    
    private final UsuarioRepository usuarioRepository;
    private final SolicitanteRepository solicitanteRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          SolicitanteRepository solicitanteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.solicitanteRepository = solicitanteRepository;
    }

    

}
