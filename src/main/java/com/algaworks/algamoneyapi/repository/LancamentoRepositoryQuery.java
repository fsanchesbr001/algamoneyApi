package com.algaworks.algamoneyapi.repository;

import com.algaworks.algamoneyapi.modelo.Lancamentos;
import com.algaworks.algamoneyapi.projection.ResumoLancamento;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {
    public Page<Lancamentos> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
