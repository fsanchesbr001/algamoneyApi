package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.exception.CategoriaInexistenteException;
import com.algaworks.algamoneyapi.modelo.Categorias;
import com.algaworks.algamoneyapi.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriasSevice {

    private final CategoriasRepository categoriasRepository;

    @Autowired
    public CategoriasSevice(CategoriasRepository categoriasRepository){
        this.categoriasRepository = categoriasRepository;
    }
    public Categorias buscaCategoriaPorId(Long codigo){
        return categoriasRepository.findById(codigo).orElseThrow(
                () -> new CategoriaInexistenteException());
    }

    public List<Categorias> listarTudo(){
        return categoriasRepository.findAll();
    }

    public Categorias salvar(Categorias categoria){
        return categoriasRepository.save(categoria);
    }
}
