package com.djeno.lab1.persistence.DTO.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCardDTO {
    private Long id;
    private String maskedCardNumber;
    private boolean isPrimary;
}
