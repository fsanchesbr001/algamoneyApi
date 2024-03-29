package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaCategoria;
import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaDia;
import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaPessoa;
import com.algaworks.algamoneyapi.exception.PessoaInativaOuInexistenteException;
import com.algaworks.algamoneyapi.mail.Mailer;
import com.algaworks.algamoneyapi.modelo.Lancamentos;
import com.algaworks.algamoneyapi.modelo.Pessoa;
import com.algaworks.algamoneyapi.projection.ResumoLancamento;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class LancamentoService {

    private LancamentoRepository lancamentoRepository;

    private PessoaService pessoaService;

    private CategoriasSevice categoriasSevice;

    private Mailer mailer;

    @Autowired
     public LancamentoService(LancamentoRepository lancamentoRepository,PessoaService pessoaService,
                              CategoriasSevice categoriasSevice,Mailer mailer){
        this.lancamentoRepository = lancamentoRepository;
        this.pessoaService = pessoaService;
        this.categoriasSevice = categoriasSevice;
        this.mailer = mailer;
    }

/*
* Método de Paginação
* */
    public Page<Lancamentos> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
        return lancamentoRepository.filtrar(lancamentoFilter,pageable);
    }


    public Optional<Lancamentos> listarPorCodigo(Long codigo)
    {
        return lancamentoRepository.findById(codigo);
    }

    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable){
        return lancamentoRepository.resumir(lancamentoFilter,pageable);
    }

    public Lancamentos salvar(Lancamentos lancamentos) {
        Pessoa pessoa = pessoaService.buscaPessoaPorId(lancamentos.getPessoa().getCodigo());
        if(pessoa==null || !pessoa.getAtivo()){
            throw new PessoaInativaOuInexistenteException();
        }
        return lancamentoRepository.save(lancamentos);
    }

    public void deletar(Long codigo) {
        lancamentoRepository.deleteById(codigo);
    }

    public Lancamentos atualizar(Long codigo, Lancamentos lancamento) {
        Lancamentos lancamentoSalvo = this.buscaLancamentoPorId(codigo);
        pessoaService.buscaPessoaPorId(lancamento.getPessoa().getCodigo());

        categoriasSevice.buscaCategoriaPorId(lancamento.getCategorias().getCodigo());

        BeanUtils.copyProperties(lancamento,lancamentoSalvo,"codigo");
        lancamentoRepository.save(lancamentoSalvo);
        return lancamentoSalvo;
    }

    public Lancamentos buscaLancamentoPorId(Long codigo){
        return lancamentoRepository.findById(codigo).orElseThrow(
                () -> new EmptyResultDataAccessException(1));
    }

    public List<LancamentoEstatisticaCategoria> porCategoria(){
        LocalDate dataAtual = LocalDate.now();

        return this.lancamentoRepository.porCategoria(dataAtual);
    }

    public List<LancamentoEstatisticaDia> porDia(){
        LocalDate dataAtual = LocalDate.now();

        return this.lancamentoRepository.porDia(dataAtual);
    }

    public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws JRException {
       List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(inicio,fim);
       Map<String,Object> parametros = new HashMap<>();
       parametros.put("DT_INICIO", Date.valueOf(inicio));
       parametros.put("DT_FIM",Date.valueOf(fim));
       parametros.put("REPORT_LOCALE",new Locale("pt","BR"));

       InputStream inputStream = this.getClass().getResourceAsStream(
                "/relatorios/lancamentos-por-pessoa.jasper");

       JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream,parametros,
                new JRBeanCollectionDataSource(dados));

       return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    @Scheduled(cron = "0 30 5 * * *")
    public void avisarSobreLancamentosVencidos(){
        mailer.avisoLancamentosVencidos();
    }


}
