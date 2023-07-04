package com.example.javaopencommerce.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderExceptionDto {

    private String message;
    private String type;
    private String additionalData;
}
