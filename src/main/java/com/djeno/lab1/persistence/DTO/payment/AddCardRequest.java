package com.djeno.lab1.persistence.DTO.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(description = "Запрос на добавление карты")
@Data
public class AddCardRequest {

    @Schema(description = "Номер карты (16-19 цифр)", example = "4111111111111111")
    @NotBlank
    private String cardNumber;

    @Schema(description = "Имя владельца карты", example = "IVAN IVANOV")
    @NotBlank
    private String cardHolder;

    @Schema(description = "Срок действия в формате MM/YY", example = "12/25")
    @NotBlank
    @Pattern(regexp = "\\d{2}/\\d{2}")
    private String expirationDate;
}
