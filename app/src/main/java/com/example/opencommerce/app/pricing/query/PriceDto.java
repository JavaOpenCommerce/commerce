package com.example.opencommerce.app.pricing.query;

import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.pricing.PriceSnapshot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {

    private ItemId itemId;
    private BigDecimal basePriceNett;
    private BigDecimal basePriceGross;
    private BigDecimal vat;
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

        return new PriceDto(snapshot.getItemId(), snapshot.getBasePriceNett()
                .asDecimal(), snapshot.getBasePriceGross()
                .asDecimal(), snapshot.getVat().asDecimal(), discount);
    }

    public record DiscountDto(BigDecimal discountedPriceNett, BigDecimal discountedPriceGross,
                              BigDecimal lowestPriceBeforeDiscountGross) {

    }

    public BigDecimal getFinalPrice() {
        return isNull(this.discount) ? this.basePriceGross : this.discount.discountedPriceGross;
    }

    public boolean discounted() {
        return nonNull(this.discount);
    }
}
