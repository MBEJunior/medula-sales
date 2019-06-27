package com.medulasales.products.repositories.extensions.impl;

import com.medulasales.products.providers.ContextProvider;
import com.medulasales.products.utils.jmgenericxrefqueryretriever.JmXRefRetriever;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

@Component
abstract class BaseRepositoryExtension implements InitializingBean {

    @Autowired
    ContextProvider contextProvider;

    @Autowired
    protected EntityManager entityManager;

    protected CriteriaBuilder criteriaBuilder;

    public <T> T getBean(Class<T> clazz) {
        return contextProvider.getBean(clazz);
    }

    public Object getBean(String beanName) {
        return contextProvider.getBean(beanName);
    }

    public  <F, T> JmXRefRetriever<F, T> createXRefRetriever
      (Class<F> fromClass, Class<T> toClass, String xRefJoinColumn, Object xRefJoinValue, Specification<F> spec, Pageable pageable) {
        return new JmXRefRetriever<F, T>(entityManager)
          .from(fromClass)
          .through(toClass)
          .withPredicateProvider((root, join, criteriaQuery, criteriaBuilder1) -> {
              Predicate predicate = null;
              Predicate joinPredicate = criteriaBuilder1.equal(join.get(xRefJoinColumn), xRefJoinValue);
              if(spec == null) {
                  predicate = joinPredicate;
              }else {
                  predicate = criteriaBuilder1.and(joinPredicate, spec.toPredicate(root, criteriaQuery, criteriaBuilder1));
              }
              return predicate;
          })
          .pageBy(pageable);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }
}
