package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.modelo.Estado;
import com.algaworks.algamoneyapi.repository.EstadoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class EstadoServiceTest {

    @Test
    void listarEstados(){
        EstadoRepository estadoRepository = Mockito.mock(EstadoRepository.class);
        List<Estado> listaEstados = listaEstadosRegiaoSul();
        EstadoService estadoService = new EstadoService(estadoRepository);
        estadoService.listar();
        Mockito.when(estadoRepository.findAll()).thenReturn(listaEstados);
        Assertions.assertEquals(3,listaEstados.size());
        Assertions.assertEquals("PR",listaEstados.get(0).getSigla());
        Assertions.assertEquals("RS",listaEstados.get(1).getSigla());
        Assertions.assertEquals("SC",listaEstados.get(2).getSigla());
    }

    private List<Estado> listaEstadosRegiaoSul(){
        List<Estado> lista = new ArrayList<>();

        Estado estado1 = new Estado();
        estado1.setCodigo(1L);
        estado1.setSigla("PR");
        estado1.setNome("Paraná");
        lista.add(estado1);

        Estado estado2 = new Estado();
        estado2.setCodigo(2L);
        estado2.setSigla("RS");
        estado2.setNome("Rio Grande do Sul");
        lista.add(estado2);

        Estado estado3 = new Estado();
        estado3.setCodigo(3L);
        estado3.setSigla("SC");
        estado3.setNome("Santa Catarina");
        lista.add(estado3);

        return lista;
    }
}
