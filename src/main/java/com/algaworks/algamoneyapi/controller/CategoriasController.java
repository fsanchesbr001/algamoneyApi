package com.algaworks.algamoneyapi.controller;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.modelo.Categorias;
import com.algaworks.algamoneyapi.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public List<Categorias> listar(){

        return categoriasRepository.findAll();
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_CATEGORIA')")
    public ResponseEntity<Categorias> adicionar(@Valid @RequestBody Categorias categoria, HttpServletResponse response){
      Categorias categoriaSalva = categoriasRepository.save(categoria);
      publisher.publishEvent(new RecursoCriadoEvent(this,response,categoriaSalva.getCodigo()));
      return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Categorias> buscaPorCodigo(@PathVariable Long codigo){
        Optional<Categorias> categoria = categoriasRepository.findById(codigo);
        return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) :ResponseEntity.notFound().build();
    }
}
