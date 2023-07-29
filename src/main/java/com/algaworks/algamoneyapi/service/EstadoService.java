package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.modelo.Estado;
import com.algaworks.algamoneyapi.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {
    private final EstadoRepository estadoRepository;

    @Autowired
    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> listar()
    {
        return estadoRepository.findAll();

    }
}
