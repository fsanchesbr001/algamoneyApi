package com.algaworks.algamoneyapi.repository;

import com.algaworks.algamoneyapi.modelo.Anexo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnexoRepository extends JpaRepository<Anexo,Long>,AnexoRepositoryQuery {

}
