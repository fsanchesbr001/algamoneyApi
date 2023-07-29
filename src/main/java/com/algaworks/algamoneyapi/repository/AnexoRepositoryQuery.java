package com.algaworks.algamoneyapi.repository;

import com.algaworks.algamoneyapi.modelo.Anexo;
import com.algaworks.algamoneyapi.repository.filter.AnexoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnexoRepositoryQuery {
    Page<Anexo> filtrarPorLancamento(AnexoFilter anexoFilter, Pageable pageable);
}
