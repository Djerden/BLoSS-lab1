package com.djeno.lab1.persistence.DTO.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddCardRequest {
    @NotBlank
    private String cardNumber;

    @NotBlank
    private String cardHolder;

    @NotBlank
    @Pattern(regexp = "\\d{2}/\\d{2}")
    private String expirationDate;
}
