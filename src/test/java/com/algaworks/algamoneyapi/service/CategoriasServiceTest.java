package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.exception.CategoriaInexistenteException;
import com.algaworks.algamoneyapi.modelo.Categorias;
import com.algaworks.algamoneyapi.repository.CategoriasRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriasServiceTest {

    @Mock
    CategoriasRepository repository;

    @Test
    void buscarCategoriaPorId(){
        MockitoAnnotations.openMocks(this);
        Categorias categoriasSalva = criaCategoriaSalva();
        CategoriasSevice service = new CategoriasSevice(repository);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(categoriasSalva));
        service.buscaCategoriaPorId(1L);
    }

    @Test
    void listarTodasCategorias(){
        MockitoAnnotations.openMocks(this);
        List<Categorias> listaCategorias = criaListaCategorias();
        CategoriasSevice service = new CategoriasSevice(repository);
        Mockito.when(repository.findAll()).thenReturn(listaCategorias);
        service.listarTudo();
    }

    @Test
    void lancaCategoriaInexistenteException(){
       MockitoAnnotations.openMocks(this);
       CategoriasSevice categoriasSevice = new CategoriasSevice(repository);
       Assertions.assertThrows(CategoriaInexistenteException.class,()-> categoriasSevice.buscaCategoriaPorId(1L));
    }

    @Test
    void testaSalvarCategoria(){
        MockitoAnnotations.openMocks(this);
        CategoriasSevice categoriasSevice = new CategoriasSevice(repository);
        Categorias novaCategoria = criaCategoriaSalva();
        categoriasSevice.salvar(novaCategoria);
        Mockito.verify(repository).save(novaCategoria);
    }

    private Categorias criaCategoriaSalva(){
        Categorias categoriasSalva = new Categorias();
        categoriasSalva.setCodigo(1L);
        categoriasSalva.setNome("Desenvolvimento");
        return categoriasSalva;
    }

    private List<Categorias> criaListaCategorias(){
        List<Categorias> lista = new ArrayList<>();

        Categorias categoria1 = new Categorias();
        categoria1.setCodigo(1L);
        categoria1.setNome("Categoria 01");
        lista.add(categoria1);

        Categorias categoria2 = new Categorias();
        categoria2.setCodigo(2L);
        categoria1.setNome("Categoria 02");
        lista.add(categoria2);

        Categorias categoria3 = new Categorias();
        categoria2.setCodigo(3L);
        categoria1.setNome("Categoria 03");
        lista.add(categoria3);

        return lista;
    }
}
