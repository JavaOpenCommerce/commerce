package com.example.javaopencommerce.image;


import com.example.javaopencommerce.item.ItemQueryRepository;
import io.vertx.mutiny.pgclient.PgPool;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.ws.rs.ext.Provider;

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
