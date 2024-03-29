package com.algaworks.algamoneyapi.repository;

import com.algaworks.algamoneyapi.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByPermissaoListDescricao(String permissaoDescricao);
}
