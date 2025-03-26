package com.djeno.lab1.persistence.DTO.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppListDto {
    private Long id;
    private String name;
    private String iconUrl;
    private BigDecimal price;
    private double averageRating;
    private int downloads;
}
