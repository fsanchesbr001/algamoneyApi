package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.modelo.Cidade;
import com.algaworks.algamoneyapi.modelo.Estado;
import com.algaworks.algamoneyapi.repository.CidadeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class CidadeServiceTest {

    @Test
    void ListaCidadesPorEstado(){
        CidadeRepository cidadeRepository = Mockito.mock(CidadeRepository.class);
        CidadeService cidadeService = new CidadeService(cidadeRepository);
        cidadeService.pesquisar(27L);
        Estado estado = geraEstado();
        List<Cidade> listaCidades = geraCidades(estado);
        Mockito.when(cidadeRepository.findByEstadoCodigo(27L)).thenReturn(listaCidades);
        Assertions.assertEquals(1,listaCidades.size());
        Assertions.assertEquals("Palmas", listaCidades.get(0).getNome());
    }

    private Estado geraEstado() {
        Estado estadoCriado = new Estado();
        estadoCriado.setCodigo(27L);
        estadoCriado.setSigla("TO");
        estadoCriado.setNome("Tocantins");
        return estadoCriado;
    }

    private List<Cidade> geraCidades(Estado estado) {
        List<Cidade> lista = new ArrayList<>();

        Cidade cidade1 = new Cidade();
        cidade1.setEstado(estado);
        cidade1.setCodigo(27L);
        cidade1.setNome("Palmas");
        lista.add(cidade1);
        return lista;
    }

}
