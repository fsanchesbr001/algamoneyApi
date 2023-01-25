package com.algaworks.algamoneyapi.repository.implementation;

import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaCategoria;
import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaDia;
import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaPessoa;
import com.algaworks.algamoneyapi.modelo.Categorias_;
import com.algaworks.algamoneyapi.modelo.Lancamentos;
import com.algaworks.algamoneyapi.modelo.Lancamentos_;
import com.algaworks.algamoneyapi.modelo.Pessoa_;
import com.algaworks.algamoneyapi.projection.ResumoLancamento;
import com.algaworks.algamoneyapi.repository.LancamentoRepositoryQuery;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Lancamentos> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamentos> criteria = builder.createQuery(Lancamentos.class);
        Root<Lancamentos> root = criteria.from(Lancamentos.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter,builder,root);
        criteria.where(predicates);

        TypedQuery<Lancamentos> query = manager.createQuery(criteria);
        adicionarRestricoesPaginacao(query,pageable);
        return new PageImpl<>(query.getResultList(),pageable,total(lancamentoFilter));
    }

    @Override
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
        Root<Lancamentos> root = criteria.from(Lancamentos.class);

        criteria.select(builder.construct(ResumoLancamento.class,
                root.get(Lancamentos_.codigo),
                root.get(Lancamentos_.descricao),
                root.get(Lancamentos_.dataVencimento),
                root.get(Lancamentos_.dataPagamento),
                root.get(Lancamentos_.valor),
                root.get(Lancamentos_.tipo),
                root.get(Lancamentos_.categorias).get(Categorias_.nome),
                root.get(Lancamentos_.pessoa).get(Pessoa_.nome)));

        Predicate[] predicates = criarRestricoes(lancamentoFilter,builder,root);
        criteria.where(predicates);

        TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
        adicionarRestricoesPaginacao(query,pageable);
        return new PageImpl<>(query.getResultList(),pageable,total(lancamentoFilter));
    }

    @Override
    public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<LancamentoEstatisticaCategoria> criteria = builder.createQuery(LancamentoEstatisticaCategoria.class);
        Root<Lancamentos> root = criteria.from(Lancamentos.class);

        criteria.select(builder.construct(LancamentoEstatisticaCategoria.class,
                root.get(Lancamentos_.categorias),
                builder.sum(root.get(Lancamentos_.valor))));

        LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
        LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

        criteria.where(
                builder.greaterThanOrEqualTo(root.get(Lancamentos_.dataVencimento),primeiroDia),
                builder.lessThanOrEqualTo(root.get(Lancamentos_.dataVencimento),ultimoDia));

        criteria.groupBy(root.get(Lancamentos_.categorias));

        TypedQuery<LancamentoEstatisticaCategoria> typedQuery = manager.createQuery(criteria);

        return typedQuery.getResultList();
    }

    @Override
    public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<LancamentoEstatisticaDia> criteria = builder.createQuery(LancamentoEstatisticaDia.class);
        Root<Lancamentos> root = criteria.from(Lancamentos.class);

        criteria.select(builder.construct(LancamentoEstatisticaDia.class,
                root.get(Lancamentos_.tipo),
                root.get(Lancamentos_.dataVencimento),
                builder.sum(root.get(Lancamentos_.valor))));

        LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
        LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

        criteria.where(
                builder.greaterThanOrEqualTo(root.get(Lancamentos_.dataVencimento),primeiroDia),
                builder.lessThanOrEqualTo(root.get(Lancamentos_.dataVencimento),ultimoDia));

        criteria.groupBy(root.get(Lancamentos_.tipo),root.get(Lancamentos_.dataVencimento));

        TypedQuery<LancamentoEstatisticaDia> typedQuery = manager.createQuery(criteria);

        return typedQuery.getResultList();
    }

    @Override
    public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<LancamentoEstatisticaPessoa> criteria = builder.createQuery(LancamentoEstatisticaPessoa.class);
        Root<Lancamentos> root = criteria.from(Lancamentos.class);

        criteria.select(builder.construct(LancamentoEstatisticaPessoa.class,
                root.get(Lancamentos_.tipo),
                root.get(Lancamentos_.pessoa.getName()),
                builder.sum(root.get(Lancamentos_.valor))));

        criteria.where(
                builder.greaterThanOrEqualTo(root.get(Lancamentos_.dataVencimento),inicio),
                builder.lessThanOrEqualTo(root.get(Lancamentos_.dataVencimento),fim));

        criteria.groupBy(root.get(Lancamentos_.tipo),root.get(Lancamentos_.pessoa));

        TypedQuery<LancamentoEstatisticaPessoa> typedQuery = manager.createQuery(criteria);

        return typedQuery.getResultList();
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter,
                                        CriteriaBuilder builder, Root<Lancamentos> root) {
        List<Predicate> predicates = new ArrayList<>();
        if(!ObjectUtils.isEmpty(lancamentoFilter.getDescricao())){
            predicates.add(builder.like(builder.lower(
                    root.get("descricao")),"%"+lancamentoFilter.getDescricao().toLowerCase()+"%"));
        }
        if(lancamentoFilter.getDataVencimentoDe()!=null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"),lancamentoFilter.getDataVencimentoDe()));
        }

        if(lancamentoFilter.getDataVencimentoAte()!=null){
            predicates.add(builder.lessThanOrEqualTo(root.get("dataVencimento"),lancamentoFilter.getDataVencimentoAte()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private Long total(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamentos> root = criteria.from(Lancamentos.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter,builder,root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();

    }

    private void adicionarRestricoesPaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int registrosPorPagina = pageable.getPageSize();
        int primeiroRegistroPagina = paginaAtual * registrosPorPagina;

        query.setFirstResult(primeiroRegistroPagina);
        query.setMaxResults(registrosPorPagina);
    }
}
