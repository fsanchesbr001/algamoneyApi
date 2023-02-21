package com.algaworks.algamoneyapi.controller;

import com.algaworks.algamoneyapi.modelo.Anexo;
import com.algaworks.algamoneyapi.repository.AnexoRepository;
import com.algaworks.algamoneyapi.repository.filter.AnexoFilter;
import com.algaworks.algamoneyapi.service.AnexoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/anexo")
public class AnexoController {
    @Autowired
    private AnexoRepository anexoRepository;

    @Autowired
    private AnexoService anexoService;


    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_READ')")
    public Page<Anexo> pesquisar(AnexoFilter anexoFilter, Pageable pageable){
        return anexoService.buscarPorLancamento(anexoFilter,pageable);
    }


    @PostMapping
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_WRITE')")
    public ResponseEntity<Anexo> incluir(@Valid @RequestBody Anexo anexo){
        Anexo anexoSalvo = anexoService.salvar(anexo);
        return ResponseEntity.status(HttpStatus.CREATED).body(anexoSalvo);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_LANCAMENTO') and hasAuthority('SCOPE_WRITE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo){
        anexoRepository.deleteById(codigo);
    }

}
