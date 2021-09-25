package com.example.javaopencommerce.item;

import com.google.common.collect.Sets;
import io.smallrye.mutiny.Uni;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;

@Log4j2
class CardRepositoryImpl implements CardRepository {

    private final RedisCardRepository redisCardRepository;
    private final ItemRepository itemRepository;

    public CardRepositoryImpl(
        RedisCardRepository redisCardRepository,
        ItemRepository itemRepository) {
        this.redisCardRepository = redisCardRepository;
        this.itemRepository = itemRepository;
    }


    @Override
    public Uni<List<Product>> getCardList(String id) {
        return restoreProducts(redisCardRepository.getCardList(id));
    }

    @Override
    public Uni<List<Product>> saveCard(String id, List<Product> products) {
        List<CardProductEntity> productEntities = products.stream()
            .map(Product::getSnapshot)
            .map(CardProductEntity::fromSnapshot)
            .collect(Collectors.toUnmodifiableList());

        return restoreProducts(redisCardRepository.saveCard(id, productEntities));
    }

    private Uni<List<Product>> restoreProducts(Uni<List<CardProductEntity>> entities) {

        return entities
            .flatMap(prods -> {
                List<Uni<Product>> productUnis = new ArrayList<>();
                for (CardProductEntity e : prods) {
                    Uni<Product> productUni = itemRepository
                        .getItemById(e.getItemId())
                        .map(item -> Product.getProduct(item, e.getAmount()));
                    productUnis.add(productUni);
                }

                //This is weird... To refactor somehow...
                return Uni.combine().all().unis(productUnis)
                    .combinedWith(products -> Sets.newHashSet((List<Product>) products))
                    .map(ArrayList::new);
            });
    }
}
