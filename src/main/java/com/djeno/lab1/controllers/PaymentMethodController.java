package com.djeno.lab1.controllers;

import com.djeno.lab1.persistence.DTO.payment.AddCardRequest;
import com.djeno.lab1.persistence.DTO.payment.PaymentCardDTO;
import com.djeno.lab1.persistence.models.PaymentMethod;
import com.djeno.lab1.services.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/payment")
@RestController
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @PostMapping("/card")
    public ResponseEntity<String> addCard(@RequestBody @Valid AddCardRequest request) {
        PaymentMethod card = paymentMethodService.addCard(request);
        return ResponseEntity.ok("Карта добавлена");
    }

    @PutMapping("/card/{id}/primary")
    public ResponseEntity<Void> setPrimaryCard(@PathVariable Long id) {
        paymentMethodService.setPrimaryCard(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cards")
    public ResponseEntity<List<PaymentCardDTO>> getUserCards() {
        List<PaymentCardDTO> cards = paymentMethodService.getUserCards();
        return ResponseEntity.ok(cards);
    }

    @DeleteMapping("/card/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable Long id) {
        paymentMethodService.deleteCard(id);
        return ResponseEntity.ok("Карта успешно удалена");
    }
}
