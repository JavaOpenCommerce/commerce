package com.example.javaopencommerce.image;


import io.vertx.mutiny.pgclient.PgPool;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
class ImageConfiguration {

  @Produces
  @ApplicationScoped
  ImageRepository imageRepository(PgPool client) {
    return new ImageRepositoryImpl(new PsqlImageRepositoryImpl(client));
  }


  @Produces
  @ApplicationScoped
  ImageQueryRepository itemQueryRepository(PgPool sqlClient) {
    return new ImageQueryRepositoryImpl(new PsqlImageRepositoryImpl(sqlClient));
  }

  @Produces
  @ApplicationScoped
  ImageController imageController(ImageQueryRepository queryRepository) {
    return new ImageController(queryRepository);
  }
}
