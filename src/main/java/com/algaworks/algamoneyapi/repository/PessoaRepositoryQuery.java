package com.algaworks.algamoneyapi.repository;

import com.algaworks.algamoneyapi.modelo.Pessoa;
import com.algaworks.algamoneyapi.repository.filter.PessoaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepositoryQuery {
    public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);
}
