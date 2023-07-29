package com.algaworks.algamoneyapi.controller;

import com.algaworks.algamoneyapi.modelo.Estado;
import com.algaworks.algamoneyapi.service.EstadoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_READ')")
    public List<Estado> listar(){
        return estadoService.listar();
    }
}

