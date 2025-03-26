package com.djeno.lab1.persistence.DTO.app;

import com.djeno.lab1.persistence.DTO.review.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDetailsDto {
    private Long id;
    private String name;
    private String description;
    private String iconUrl;
    private List<String> screenshotUrls;
    private BigDecimal price;
    private double averageRating;
    private int downloads;
    private LocalDateTime createdAt;
    private String ownerUsername;
    private List<String> categories;
    private List<ReviewDTO> reviews;
}
