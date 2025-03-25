package com.djeno.lab1.persistence.repositories;

import com.djeno.lab1.persistence.models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

}
