package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.item.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductModelTest {

    Product product;
    CardItem itemModel;


    @ParameterizedTest(name = "For given item price {0} amount {1} and vat {2} GrossValue should equal {3}, NettValue equal {4}")
    @CsvSource({
            "0.00, 0, 0, 0.00, 0.00",
            "0.00, 0, 0.23, 0.00, 0.00",
            "0.00, 1, 0.23, 0.00, 0.00",
            "-1.00, 2, 0.99, 0.00, 0.00",
            "1.00, -3, 0.23, 1.00, 0.81",
            "1.00, 1, -3.00, 1.00, 1.00",
            "1.00, 1, 0.23, 1.00, 0.81",
            "2.33, 3, 0.07, 6.99, 6.53",
            "6.13, 0, 0.22, 6.13, 5.02",
            "0.01, 2, 0.03, 0.02, 0.02",
            "10.00, 5, 0.00, 50.00, 50.00"
    })
    void setAmount(BigDecimal value, int amount, double vat, BigDecimal gross, BigDecimal nett) {

        //given
        itemModel = CardItem.builder()
                .id(1L)
                .valueGross(Value.of(value))
                .vat(Vat.of(vat))
                .build();

        product = Product.getProduct(itemModel);

        //when
        product.setAmount(amount);

        //then
        Assertions.assertEquals(gross, product.getValueGross().asDecimal());
        Assertions.assertEquals(nett, product.getValueNett().asDecimal());
    }
}
