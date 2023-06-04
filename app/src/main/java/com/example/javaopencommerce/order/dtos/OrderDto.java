package com.example.javaopencommerce.order.dtos;

import com.example.javaopencommerce.item.dtos.CardDto;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

  private Long id;
  private LocalDate creationDate;
  private String paymentStatus;
  private String paymentMethod;
  private String orderStatus;
  private Long addressId;
  private CardDto card;
}
