package com.algaworks.algamoneyapi.repository.implementation;

import com.algaworks.algamoneyapi.modelo.Lancamentos;
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
        return null;
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter,
                                        CriteriaBuilder builder, Root<Lancamentos> root) {
        List<Predicate> predicates = new ArrayList<>();
        if(!ObjectUtils.isEmpty(lancamentoFilter.getDescricao())){
            predicates.add(builder.like(builder.lower(
                    root.get("descricao")),"%"+lancamentoFilter.getDescricao().toLowerCase()+"%"));
        }
        if(lancamentoFilter.getDataVencimentoDe()!=null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimentoDe"),lancamentoFilter.getDataVencimentoDe()));
        }

        if(lancamentoFilter.getDataVencimentoAte()!=null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimentoAte"),lancamentoFilter.getDataVencimentoAte()));
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

    private void adicionarRestricoesPaginacao(TypedQuery<Lancamentos> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int registrosPorPagina = pageable.getPageSize();
        int primeiroRegistroPagina = paginaAtual * registrosPorPagina;

        query.setFirstResult(primeiroRegistroPagina);
        query.setMaxResults(registrosPorPagina);
    }
}
