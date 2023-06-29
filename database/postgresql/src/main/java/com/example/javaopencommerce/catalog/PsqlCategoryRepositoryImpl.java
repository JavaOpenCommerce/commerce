package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.exception.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;


class PsqlCategoryRepositoryImpl implements PsqlCategoryRepository {

  private final EntityManager em;

  PsqlCategoryRepositoryImpl(EntityManager em) {
    this.em = em;
  }

  @Override
  public String getCatalog() {
    // TODO try to get rid of casting.
    try {
      return (String) em.createNativeQuery("SELECT c.body FROM category c WHERE id = 1 LIMIT 1")
          .getSingleResult();
    } catch (NoResultException e) {
      throw new EntityNotFoundException("Catalog");
    }
  }

  @Override
  public String saveCatalog(String catalog) {
    // TODO Rewrite to check if change was actually made.
    return (String) em.createNativeQuery(
            "UPDATE category c SET c.body = ?1 WHERE c.id = 1 RETURNING c.body")
        .getSingleResult();
  }
}
