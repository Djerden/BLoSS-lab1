package com.djeno.lab1.services;

import com.djeno.lab1.exceptions.CardNotFoundException;
import com.djeno.lab1.exceptions.LastPrimaryCardException;
import com.djeno.lab1.exceptions.PaymentProcessingException;
import com.djeno.lab1.exceptions.PrimaryCardNotFoundException;
import com.djeno.lab1.persistence.DTO.payment.AddCardRequest;
import com.djeno.lab1.persistence.DTO.payment.PaymentCardDTO;
import com.djeno.lab1.persistence.models.PaymentMethod;
import com.djeno.lab1.persistence.models.User;
import com.djeno.lab1.persistence.repositories.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final UserService userService;
    private final Random random = new Random();

    public PaymentMethod addCard(AddCardRequest request) {
        User user = userService.getCurrentUser();

        PaymentMethod card = new PaymentMethod();
        card.setUser(user);
        card.setCardNumber(request.getCardNumber());
        card.setCardHolder(request.getCardHolder());
        card.setExpirationDate(request.getExpirationDate());
        card.setPrimary(user.getPaymentMethods().isEmpty()); // Первая карта становится основной

        return paymentMethodRepository.save(card);
    }

    public void setPrimaryCard(Long cardId) {
        User user = userService.getCurrentUser();
        PaymentMethod newPrimary = paymentMethodRepository.findByIdAndUser(cardId, user)
                .orElseThrow(() -> new CardNotFoundException("Карта не найдена"));

        user.getPaymentMethods().forEach(card -> {
            card.setPrimary(card.getId().equals(cardId));
            paymentMethodRepository.save(card);
        });
    }

    public List<PaymentCardDTO> getUserCards() {
        User user = userService.getCurrentUser();
        List<PaymentMethod> cards = paymentMethodRepository.findByUser(user);

        return cards.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCard(Long cardId) {
        User currentUser = userService.getCurrentUser();
        PaymentMethod card = paymentMethodRepository.findByIdAndUser(cardId, currentUser)
                .orElseThrow(() -> new CardNotFoundException("Карта не найдена или не принадлежит пользователю"));

        // Нельзя удалить основную карту, если она последняя
        if (card.isPrimary() && paymentMethodRepository.countByUser(currentUser) == 1) {
            throw new LastPrimaryCardException("Нельзя удалить единственную основную карту");
        }

        paymentMethodRepository.delete(card);

        // Если удалили основную карту - назначаем новую основную
        if (card.isPrimary()) {
            paymentMethodRepository.findFirstByUser(currentUser)
                    .ifPresent(newPrimary -> {
                        newPrimary.setPrimary(true);
                        paymentMethodRepository.save(newPrimary);
                    });
        }
    }

    private PaymentCardDTO convertToDto(PaymentMethod card) {
        return PaymentCardDTO.builder()
                .id(card.getId())
                .maskedCardNumber(maskCardNumber(card.getCardNumber()))
                .isPrimary(card.isPrimary())
                .build();
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 12) {
            return "****"; // Запасной вариант для некорректных номеров
        }
        // Оставляем первые 4 и последние 4 цифры, остальное маскируем
        String firstFour = cardNumber.substring(0, 4);
        String lastFour = cardNumber.substring(cardNumber.length() - 4);
        return firstFour + " **** **** " + lastFour;
    }

    public boolean processPayment(User user, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return true;
        }

        PaymentMethod primaryCard = paymentMethodRepository.findByUserAndIsPrimary(user, true)
                .orElseThrow(() -> new PrimaryCardNotFoundException("Основная карта не найдена"));

        // Имитация оплаты - 50% успешных платежей
        boolean paymentSuccess = random.nextDouble() < 0.5;

        if (!paymentSuccess) {
            throw new PaymentProcessingException("Недостаточно средств на карте");
        }

        return true;
    }
}
