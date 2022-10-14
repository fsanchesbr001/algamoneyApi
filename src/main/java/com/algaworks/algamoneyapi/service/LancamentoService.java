package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.exception.PessoaInativaOuInexistenteException;
import com.algaworks.algamoneyapi.modelo.Lancamentos;
import com.algaworks.algamoneyapi.modelo.Pessoa;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;
/*
* Método de Paginação
* */
    public Page<Lancamentos> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
        return lancamentoRepository.filtrar(lancamentoFilter,pageable);
    }
    public List<Lancamentos> listarTudo()
    {
        return lancamentoRepository.findAll();

    }

    public Optional<Lancamentos> listarPorCodigo(Long codigo)
    {
        return lancamentoRepository.findById(codigo);
    }

    public Lancamentos salvar(Lancamentos lancamentos) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(lancamentos.getPessoa().getCodigo());
        if(!pessoa.isPresent() || !pessoa.get().getAtivo()){
            throw new PessoaInativaOuInexistenteException();
        }
        return lancamentoRepository.save(lancamentos);
    }

    public void deletar(Long codigo) {
        lancamentoRepository.deleteById(codigo);
    }
}
