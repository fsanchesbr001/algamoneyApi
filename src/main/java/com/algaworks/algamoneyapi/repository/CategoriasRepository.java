package com.algaworks.algamoneyapi.repository;

import com.algaworks.algamoneyapi.modelo.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriasRepository extends JpaRepository<Categorias, Long> {
}