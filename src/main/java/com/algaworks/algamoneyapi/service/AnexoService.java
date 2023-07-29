package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.exception.LancamentoInexistenteException;
import com.algaworks.algamoneyapi.modelo.Anexo;
import com.algaworks.algamoneyapi.modelo.Lancamentos;
import com.algaworks.algamoneyapi.repository.AnexoRepository;
import com.algaworks.algamoneyapi.repository.filter.AnexoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnexoService {

    @Autowired
    private LancamentoService lancamentoService;
    @Autowired
    private AnexoRepository anexoRepository;


    public Anexo salvar(Anexo anexo) {
        Optional<Lancamentos> lancamento = lancamentoService.listarPorCodigo(anexo.getLancamento().getCodigo());
        if(!lancamento.isPresent()){
            throw new LancamentoInexistenteException();
        }
        return anexoRepository.save(anexo);
    }

    public Page<Anexo> buscarPorLancamento(AnexoFilter anexoFilter, Pageable pageable) {
        return anexoRepository.filtrarPorLancamento(anexoFilter,pageable);
    }
}
