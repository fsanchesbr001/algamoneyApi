package com.algaworks.algamoneyapi.repository;

import com.algaworks.algamoneyapi.modelo.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
}
