package com.algaworks.algamoneyapi.repository;

import com.algaworks.algamoneyapi.modelo.Lancamentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamentos,Long>,LancamentoRepositoryQuery {
    List<Lancamentos> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);
}
