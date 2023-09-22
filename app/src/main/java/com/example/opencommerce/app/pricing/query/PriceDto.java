package com.example.opencommerce.app.pricing.query;

import com.example.opencommerce.domain.pricing.PriceSnapshot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {

    private BigDecimal basePriceNett;
    private BigDecimal basePriceGross;
    private DiscountDto discount;

    public static PriceDto fromSnapshot(PriceSnapshot snapshot) {
        DiscountDto discount = null;
        if (snapshot.getDiscount() != null) {
            PriceSnapshot.Discount discountSnapshot = snapshot.getDiscount();
            discount = new DiscountDto(discountSnapshot.discountedPriceNett()
                    .asDecimal(), discountSnapshot.discountedPriceGross()
                    .asDecimal(), discountSnapshot.lowestPriceBeforeDiscountGross()
                    .asDecimal());
        }

        return new PriceDto(snapshot.getBasePriceNett()
                .asDecimal(), snapshot.getBasePriceGross()
                .asDecimal(), discount);
    }

    public record DiscountDto(BigDecimal discountedPriceNett, BigDecimal discountedPriceGross,
                              BigDecimal lowestPriceBeforeDiscountGross) {

    }
}
