package com.djeno.lab1.persistence.DTO.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateReviewRequest {
    @NotNull(message = "ID приложения обязательно")
    private Long appId;

    @NotNull(message = "Рейтинг обязателен")
    @Min(value = 1, message = "Рейтинг должен быть от 1 до 5")
    @Max(value = 5, message = "Рейтинг должен быть от 1 до 5")
    private Integer rating;

    @Size(max = 1000, message = "Комментарий не должен превышать 1000 символов")
    private String comment;
}
