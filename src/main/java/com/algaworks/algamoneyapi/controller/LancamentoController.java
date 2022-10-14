package com.algaworks.algamoneyapi.controller;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.modelo.Lancamentos;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;
import com.algaworks.algamoneyapi.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {


    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public Page<Lancamentos> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
        return lancamentoService.pesquisar(lancamentoFilter,pageable);
    }
    /*@GetMapping
    public List<Lancamentos> listar(){
        return lancamentoService.listarTudo();
    }*/

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamentos> listarPorCodigo(@PathVariable Long codigo){
        Optional<Lancamentos> lancamento = lancamentoService.listarPorCodigo(codigo);
        return lancamento.isPresent()? ResponseEntity.ok(lancamento.get()) :
               ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Lancamentos> salvar(@Valid @RequestBody Lancamentos lancamentos, HttpServletResponse response){
        Lancamentos lancamentoSalvo = lancamentoService.salvar(lancamentos);
        publisher.publishEvent(new RecursoCriadoEvent(this,response,lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long codigo){
        lancamentoService.deletar(codigo);
    }
}
