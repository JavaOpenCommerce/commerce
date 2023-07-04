package com.example.javaopencommerce.catalog;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;

class ItemConfiguration {

    @Produces
    @ApplicationScoped
    PsqlItemRepository psqlItemRepository(EntityManager entityManager) {
        return new PsqlItemRepositoryImpl(entityManager);
    }

    @Produces
    @ApplicationScoped
    ItemDtoFactory dtoFactory() {
        return new ItemDtoFactory();
    }

    @Produces
    @ApplicationScoped
    ItemQueryRepository itemQueryRepository(PsqlItemRepository repository,
                                            ItemDtoFactory factory) {
        return new ItemQueryRepositoryImpl(repository, factory);
    }

    @Produces
    @ApplicationScoped
    ItemQueryFacade itemQueryFacade(SearchService searchService,
                                    ItemQueryRepository repository) {
        return new ItemQueryFacade(searchService, repository);
    }

    @Produces
    @ApplicationScoped
    ItemController itemController(ItemQueryRepository queryRepository,
                                  ItemQueryFacade queryFacade) {
        return new ItemController(queryRepository, queryFacade);
    }
}
