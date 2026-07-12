package br.ufes.reserva_espacos.controller;

import org.springframework.web.bind.annotation.RestController;

import br.ufes.reserva_espacos.dto.usuariodto.CadastroAdministradorDTO;
import br.ufes.reserva_espacos.entity.Administrador;
import br.ufes.reserva_espacos.service.AdministradorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final AdministradorService administradorService;


    public AdministradorController(
            AdministradorService administradorService) {

        this.administradorService = administradorService;
    }


    @PostMapping
    public Administrador cadastrar(
            @RequestBody CadastroAdministradorDTO dto) {

        return administradorService.cadastrar(dto);
    }

}
