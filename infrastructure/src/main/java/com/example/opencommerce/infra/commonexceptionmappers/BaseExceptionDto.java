package com.example.opencommerce.infra.commonexceptionmappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseExceptionDto {

    private String message;
    private String type;
    private String additionalData;
}
