package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.exception.CategoriaInexistenteException;
import com.algaworks.algamoneyapi.modelo.Categorias;
import com.algaworks.algamoneyapi.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriasSevice {
    @Autowired
    CategoriasRepository categoriasRepository;

    public Categorias buscaCategoriaPorId(Long codigo){
        Categorias categoriaSalva = categoriasRepository.findById(codigo).orElseThrow(
                () -> new CategoriaInexistenteException());
        return  categoriaSalva;
    }
}
