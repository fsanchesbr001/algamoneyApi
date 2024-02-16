package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.exception.PessoaInativaOuInexistenteException;
import com.algaworks.algamoneyapi.modelo.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import com.algaworks.algamoneyapi.repository.filter.PessoaFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository){
        this.pessoaRepository = pessoaRepository;
    }

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
        Optional<Pessoa> pessoaSalva = Optional.ofNullable(pessoaRepository.findById(codigo)
                .orElseThrow(() -> new PessoaInativaOuInexistenteException()));
        return pessoaSalva.orElse(null);
    }

    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listarTudo(){
        return pessoaRepository.findAll();
    }

    public void deletar(Long codigo){
        pessoaRepository.deleteById(codigo);
    }

    public Page<Pessoa> filtraRetorno(PessoaFilter pessoaFilter, Pageable pageable){
        return pessoaRepository.filtrar(pessoaFilter,pageable);
    }
}
