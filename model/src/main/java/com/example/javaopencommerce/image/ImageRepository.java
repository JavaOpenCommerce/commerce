package com.example.javaopencommerce.image;

import io.smallrye.mutiny.Uni;

interface ImageRepository {

  Uni<Image> saveImage(Image image);

}
