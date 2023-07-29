package com.algaworks.algamoneyapi.controller;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.modelo.Categorias;
import com.algaworks.algamoneyapi.service.CategoriasSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {

    @Autowired
    private CategoriasSevice categoriasSevice;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_CATEGORIA') and hasAuthority('SCOPE_READ')")
    public List<Categorias> listar(){
        return categoriasSevice.listarTudo();
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_CATEGORIA') and hasAuthority('SCOPE_WRITE')")
    public ResponseEntity<Categorias> adicionar(@Valid @RequestBody Categorias categoria, HttpServletResponse response){
      Categorias categoriaSalva = categoriasSevice.salvar(categoria);
      publisher.publishEvent(new RecursoCriadoEvent(this,response,categoriaSalva.getCodigo()));
      return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_CATEGORIA') and hasAuthority('SCOPE_READ')")
    public ResponseEntity<Categorias> buscaPorCodigo(@PathVariable Long codigo){
        Categorias categoria = categoriasSevice.buscaCategoriaPorId(codigo);
        return categoria!=null ? ResponseEntity.ok(categoria) :ResponseEntity.notFound().build();
    }
}
