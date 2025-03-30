package com.dream.restapi.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CabinRequestDto {
    private String name;
    private int capacity;
    private BigDecimal price;
    private String status;
    private String location;
    private String description;
}
