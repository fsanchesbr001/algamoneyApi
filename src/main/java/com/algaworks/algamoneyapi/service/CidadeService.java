package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.modelo.Cidade;
import com.algaworks.algamoneyapi.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    @Autowired
    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public List<Cidade> pesquisar(Long estado){
        return cidadeRepository.findByEstadoCodigo(estado);
    }

}
