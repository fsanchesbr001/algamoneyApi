package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.exception.PessoaInativaOuInexistenteException;
import com.algaworks.algamoneyapi.modelo.Lancamentos;
import com.algaworks.algamoneyapi.modelo.Pessoa;
import com.algaworks.algamoneyapi.projection.ResumoLancamento;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private CategoriasSevice categoriasSevice;
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

    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable){
        return lancamentoRepository.resumir(lancamentoFilter,pageable);
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

    public Lancamentos atualizar(Long codigo, Lancamentos lancamento) {
        Lancamentos lancamentoSalvo = this.buscaLancamentoPorId(codigo);
        pessoaService.buscaPessoaPorId(lancamento.getPessoa().getCodigo());

        categoriasSevice.buscaCategoriaPorId(lancamento.getCategorias().getCodigo());

        BeanUtils.copyProperties(lancamento,lancamentoSalvo,"codigo");
        lancamentoRepository.save(lancamentoSalvo);
        return lancamentoSalvo;
    }

    public Lancamentos buscaLancamentoPorId(Long codigo){
        Lancamentos lancamentoSalvo = lancamentoRepository.findById(codigo).orElseThrow(
                () -> new EmptyResultDataAccessException(1));
        return  lancamentoSalvo;
    }
}
