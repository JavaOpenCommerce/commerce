package com.example.javaopencommerce.catalog;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.example.javaopencommerce.catalog.Category.CategorySnapshot;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class CatalogTest {

  static final Category CATALOG_ROOT = Category.newCatalog();
  static final Category ROAD_BIKES = CATALOG_ROOT.addChildToThisCategory("Road bikes",
      "Light racing bikes");
  static final Category GRAVELS = ROAD_BIKES.addChildToThisCategory("Gravels",
      "If u want speed but also want to go rough");
  static final Category ENDURANCE = ROAD_BIKES.addChildToThisCategory("Endurance",
      "Go Fast and light.");
  static final Category RACE = ROAD_BIKES.addChildToThisCategory("Race",
      "Racing bikes, no compromise.");
  static final Category MOUNTAIN_BIKES = CATALOG_ROOT.addChildToThisCategory("Mountain Bikes",
      "Something for mountain adventure enjoyers");
  static final Category ENDURO = MOUNTAIN_BIKES.addChildToThisCategory("Enduro",
      "Extreme downhills, but also uphills");
  static final Category DOWNHILL = MOUNTAIN_BIKES.addChildToThisCategory("Downhill",
      "Only one purpose, go down as fast as possible");

  @Test
  void shouldFindAllCategoriesGoingDownFromRoot() {
    // when
    AtomicInteger counter = new AtomicInteger(0);
    countCatalogSize(CATALOG_ROOT, counter);

    // then
    assertThat(counter.get()).isEqualTo(8);
  }

  @Test
  void shouldFindAllSubcategoriesOfEnduroCategory() {
    // given
    CategoryId id = ENDURO.toSnapshot().id();

    // when
    List<CategoryId> subcategories = CATALOG_ROOT.findAllSubcategoryIdsFor(id);

    // then
    assertThat(subcategories).hasSize(2)
        .contains(MOUNTAIN_BIKES.toSnapshot().id(), CATALOG_ROOT.toSnapshot().id());

  }

  @Test
  void shouldFindAllSubcategoriesOfGravelsSCategory() {
    // given
    CategoryId id = GRAVELS.toSnapshot().id();

    // when
    List<CategoryId> subcategories = ROAD_BIKES.findAllSubcategoryIdsFor(id);

    // then
    assertThat(subcategories).hasSize(2)
        .contains(ROAD_BIKES.toSnapshot().id(), CATALOG_ROOT.toSnapshot().id());

  }

  @Test
  void shouldAddNewCategoryAsSubcategoryOfEnduroAndThenRemoveIt() {

    // when
    CATALOG_ROOT.addChildToCategoryWithId(ENDURO.toSnapshot().id(), "All Mountain",
        "Cross Country approach");

    // then
    assertThat(ENDURO.getChildren()).hasSize(1);
    Category allMountainCategory = ENDURO.getChildren().stream().toList().get(0);
    assertThat(allMountainCategory).extracting(Category::toSnapshot)
        .extracting(
            CategorySnapshot::name).isEqualTo("All Mountain");

    // when
    CATALOG_ROOT.removeCategoryWithId(allMountainCategory.toSnapshot().id());

    // then
    assertThat(ENDURO.getChildren()).isEmpty();
  }

  private void countCatalogSize(Category category, AtomicInteger counter) {
    counter.incrementAndGet();
    for (Category child : category.getChildren()) {
      countCatalogSize(child, counter);
    }
  }
}
