package com.medulasales.products.utils.jmgenericxrefqueryretriever;

import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

public class JmXRefRetriever<F, T> {

    @FunctionalInterface
    public static interface SelectionTransformer<F> {
        Selection transform(Root<F> root, CriteriaBuilder criteriaBuilder);
    }

    @FunctionalInterface
    public static interface PredicateProvider<F, T> {
        Predicate provide(Root<F> root, Join<F, T> join, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder);
    }

    private EntityManager entityManager;
    private Class<F> fromClass;
    private Class<T> throughClass;
    private PredicateProvider<F, T> predicateProvider;
    private SelectionTransformer<F> selectionTransformer;
    private Sort sort;
    private String collectionFieldName;
    private String countFieldName;
    private Pageable pageable;
    private CriteriaQuery criteriaQuery;
    private Root<F> queryRoot;

    public JmXRefRetriever(EntityManager em) {
        this.entityManager = em;
    }

    public JmXRefRetriever<F, T> from(Class<F> fromClass) {
        this.fromClass = fromClass;
        return this;
    }

    public JmXRefRetriever<F, T> through(Class<T> through) {
        this.throughClass = through;
        return this;
    }

    public JmXRefRetriever<F, T> withSelectionTransformer(SelectionTransformer<F> selectionTransformer) {
        this.selectionTransformer = selectionTransformer;
        return this;
    }

    public JmXRefRetriever<F, T> withPredicateProvider(PredicateProvider<F, T> predicate) {
        this.predicateProvider = predicate;
        return this;
    }

    public JmXRefRetriever<F, T> collectBy(String collectionFieldName) {
        this.collectionFieldName = collectionFieldName;
        return this;
    }

    public JmXRefRetriever<F, T> countBy(String countFieldName) {
        this.countFieldName = countFieldName;
        return this;
    }

    public JmXRefRetriever<F, T> sortBy(Sort sort) {
        this.sort = sort;
        return this;
    }

    public JmXRefRetriever<F, T> pageBy(Pageable pageable) {
        this.pageable = pageable;
        return this;
    }

    @SuppressWarnings("unchecked")
    private <P> void buildCriteriaQuery(Class<P> projectionClass) throws JmXRefException {
        if(this.entityManager == null)
            throw new JmXRefException("Entity manager cannot be null");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        criteriaQuery = cb.createQuery(projectionClass);
        queryRoot = criteriaQuery.from(fromClass);
        Join<F, T> xRefJoin = queryRoot.join(collectionFieldName, JoinType.INNER);

        criteriaQuery.select(selectionTransformer != null ? selectionTransformer.transform(queryRoot, cb) : queryRoot);
        if(predicateProvider != null) {
            criteriaQuery.where(predicateProvider.provide(queryRoot, xRefJoin, criteriaQuery, cb));
        }

        if(sort != null || pageable != null) {
            Sort rSort = sort != null ? sort : pageable.getSort();
            if(rSort != null) {
                List<Order> orders = rSort.stream()
                  .map((Sort.Order order)-> {
                      return JmOrderImpl.of(queryRoot.get(order.getProperty()), order.isAscending());
                  }).collect(Collectors.toList());
                criteriaQuery.orderBy(orders);
            }
        }

    }

    @SuppressWarnings("unchecked")
    public <P> P retrieveOne(Class<P> clazz) throws JmXRefException {
        buildCriteriaQuery(clazz);
        Query query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(0).setMaxResults(1);
        return (P) query.getSingleResult();
    }

    public Long count() throws JmXRefException {
        if(countFieldName == null)
            throw new JmXRefException("You must provide addField setName to perform count");
        return new JmXRefRetriever<F, T>(entityManager)
          .from(fromClass)
          .through(throughClass)
          .collectBy(collectionFieldName)
          .withPredicateProvider(predicateProvider)
          .withSelectionTransformer((root, criteriaBuilder)-> {
              return criteriaBuilder.count(root.get(countFieldName));
          })
          .sortBy(sort)
          .retrieveOne(Long.class);
    }

    @SuppressWarnings("unchecked")
    public <P> List<P> retrieveList(Class<P> clazz) throws JmXRefException {
        buildCriteriaQuery(clazz);
        Query query = entityManager.createQuery(criteriaQuery);
        return (List<P>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public <P> Page<P> retrievePage(Class<P> clazz) throws JmXRefException {
        Long totalCount = this.count();
        Pageable rPageable = this.pageable != null ? this.pageable : PageRequest.of(1, 10);
        int lastPageNumber = (int) (Math.ceil(totalCount / pageable.getPageSize()));
        Query query = entityManager.createQuery(criteriaQuery);
        query
          .setFirstResult((lastPageNumber - 1) * rPageable.getPageSize())
          .setMaxResults(rPageable.getPageSize());

        return new PageImpl<P>(query.getResultList(), pageable, totalCount);
    }
}
