package com.algaworks.algamoneyapi.repository.implementation;

import com.algaworks.algamoneyapi.modelo.Anexo;
import com.algaworks.algamoneyapi.repository.AnexoRepositoryQuery;
import com.algaworks.algamoneyapi.repository.filter.AnexoFilter;
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

public class AnexoRepositoryImpl implements AnexoRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Anexo> filtrarPorLancamento(AnexoFilter anexoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Anexo> criteria = builder.createQuery(Anexo.class);
        Root<Anexo> root = criteria.from(Anexo.class);

        Predicate[] predicates = criarRestricoes(anexoFilter,builder,root);
        criteria.where(predicates);

        TypedQuery<Anexo> query = manager.createQuery(criteria);
        adicionarRestricoesPaginacao(query,pageable);
        return new PageImpl<>(query.getResultList(),pageable,total(anexoFilter));
    }

    private Predicate[] criarRestricoes(AnexoFilter anexoFilter,
                                        CriteriaBuilder builder, Root<Anexo> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(anexoFilter.getLancamentoId()!=null){
            predicates.add(builder.equal(root.get("lancamento"),anexoFilter.getLancamentoId()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private Long total(AnexoFilter anexoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Anexo> root = criteria.from(Anexo.class);

        Predicate[] predicates = criarRestricoes(anexoFilter,builder,root);
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
