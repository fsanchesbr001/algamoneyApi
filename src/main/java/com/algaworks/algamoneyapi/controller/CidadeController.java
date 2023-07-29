package com.algaworks.algamoneyapi.controller;

import com.algaworks.algamoneyapi.modelo.Cidade;
import com.algaworks.algamoneyapi.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_READ')")
    public List<Cidade> pesquisar(@RequestParam Long estado){
        return cidadeService.pesquisar(estado);
    }
}
