package com.example.opencommerce.adapters.database.warehouse.sql;

import com.example.opencommerce.app.warehouse.query.ItemStockDto;
import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.OrderId;
import com.example.opencommerce.domain.warehouse.ItemReservation;
import com.example.opencommerce.domain.warehouse.ItemReservationId;
import com.example.opencommerce.domain.warehouse.ItemStock;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.opencommerce.app.warehouse.query.ItemStockDto.StockReservationDto;

@ApplicationScoped
class ItemMapper {

    ItemStock toModel(ItemStockEntity item) {
        return ItemStock.from(
                ItemId.of(item.getId()),
                Amount.of(item.getStock()),
                item.getReservations()
                        .stream()
                        .map(this::toModel)
                        .collect(Collectors.toSet())
        );
    }

    ItemStockEntity toEntity(ItemStock item) {
        List<ItemReservationEntity> reservations = item.reservations()
                .stream()
                .map(this::toEntity)
                .toList();

        ItemStockEntity itemStock = new ItemStockEntity(
                item.id()
                        .asLong(),
                item.quantityOnHand()
                        .asInteger(),
                reservations
        );

        itemStock.getReservations()
                .forEach(reservation -> reservation.setItemStock(itemStock));
        return itemStock;
    }

    private ItemReservation toModel(ItemReservationEntity reservation) {
        return ItemReservation.restore(
                ItemReservationId.of(reservation.getId()),
                OrderId.from(reservation.getOrderId()),
                Amount.of(reservation.getReservedAmount())
        );
    }

    private ItemReservationEntity toEntity(ItemReservation reservation) {
        return new ItemReservationEntity(
                reservation.id()
                        .asLong(),
                reservation.orderId()
                        .asUUID(),
                reservation.reservedAmount()
                        .asInteger(),
                null);
    }

    public ItemStockDto toDto(ItemStockEntity item) {
        List<StockReservationDto> reservations = item.getReservations()
                .stream()
                .map(reservation ->
                        new StockReservationDto(reservation.getOrderId(), reservation.getReservedAmount()))
                .toList();

        Integer availableStock = item.getStock() - reservations.stream()
                .map(StockReservationDto::amountReserved)
                .reduce(0, Integer::sum);

        return new ItemStockDto(item.getId(), item.getStock(), availableStock, reservations);
    }
}
