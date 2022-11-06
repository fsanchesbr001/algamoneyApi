package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.exception.PessoaInativaOuInexistenteException;
import com.algaworks.algamoneyapi.modelo.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(Long codigo, Pessoa pessoa){
        Pessoa pessoaSalva = buscaPessoaPorId(codigo);
        BeanUtils.copyProperties(pessoa,pessoaSalva,"codigo");
        pessoaRepository.save(pessoaSalva);
        return pessoaSalva;
    }

    public void atualizarAtivo(Long codigo,Boolean ativo){
        Pessoa pessoaSalva = buscaPessoaPorId(codigo);
        pessoaSalva.setAtivo(ativo);
        pessoaRepository.save(pessoaSalva);
    }

    public Pessoa buscaPessoaPorId(Long codigo){
        Pessoa pessoaSalva = pessoaRepository.findById(codigo).orElseThrow(
                () -> new PessoaInativaOuInexistenteException());
        if(!pessoaSalva.getAtivo()){
            throw  new PessoaInativaOuInexistenteException();
        }
        return  pessoaSalva;
    }
}
