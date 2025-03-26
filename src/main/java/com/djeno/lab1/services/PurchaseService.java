package com.djeno.lab1.services;

import com.djeno.lab1.persistence.models.App;
import com.djeno.lab1.persistence.models.Purchase;
import com.djeno.lab1.persistence.models.User;
import com.djeno.lab1.persistence.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserService userService;
    private final AppService appService;
    private final PaymentMethodService paymentMethodService;

    public boolean hasUserPurchasedApp(User user, App app) {
        return purchaseRepository.existsByUserAndApp(user, app);
    }

    public Purchase purchaseApp(Long id) {
        User user = userService.getCurrentUser();
        App app = appService.getAppById(id);

        // Проверяем, не куплено ли уже
        if (hasUserPurchasedApp(user, app)) {
            throw new RuntimeException("Приложение уже приобретено");
        }

        // Пытаемся провести оплату
        boolean paymentSuccess = paymentMethodService.processPayment(user, app.getPrice());

        if (!paymentSuccess) {
            throw new RuntimeException("Оплата не прошла. Проверьте платежные данные");
        }

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setApp(app);
        purchase.setPurchaseDate(LocalDateTime.now());

        return purchaseRepository.save(purchase);
    }
}
