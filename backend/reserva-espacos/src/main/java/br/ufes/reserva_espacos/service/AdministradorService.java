package br.ufes.reserva_espacos.service;

import br.ufes.reserva_espacos.repositories.AdministradorRepository;

public class AdministradorService {
    private final AdministradorRepository administradorRepository;

    public AdministradorService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }
}