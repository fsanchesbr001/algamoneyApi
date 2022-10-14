package com.algaworks.algamoneyapi.repository;

import com.algaworks.algamoneyapi.modelo.Lancamentos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamentos,Long>,LancamentoRepositoryQuery {
}
