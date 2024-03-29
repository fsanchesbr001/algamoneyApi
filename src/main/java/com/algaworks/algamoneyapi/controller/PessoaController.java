package com.algaworks.algamoneyapi.controller;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.modelo.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import com.algaworks.algamoneyapi.repository.filter.PessoaFilter;
import com.algaworks.algamoneyapi.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping()
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_READ')")
    public List<Pessoa> listar(){
        return pessoaService.listarTudo();
    }

    @GetMapping(params = "filtrar")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_READ')")
    public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable){
        return pessoaService.filtraRetorno(pessoaFilter,pageable);
    }


    @GetMapping("/{codigo}")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_READ')")
    public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable Long codigo){
        Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
        return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_WRITE')")
    public ResponseEntity<Pessoa> incluir(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
        Pessoa pessoaSalva = pessoaService.salvar(pessoa);
        publisher.publishEvent(new RecursoCriadoEvent(this,response,pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize(value = "hasAuthority('ROLE_REMOVER_PESSOA') and hasAuthority('SCOPE_WRITE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo){
        pessoaService.deletar(codigo);
    }

    @PutMapping("/{codigo}")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_WRITE')")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo
            , @Valid @RequestBody Pessoa pessoa){
        Pessoa pessoaSalva = pessoaService.atualizar(codigo,pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }

    @PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_WRITE')")
    public void atualizarAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo){
        pessoaService.atualizarAtivo(codigo,ativo);
    }
}
