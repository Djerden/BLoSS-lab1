package com.djeno.lab1.persistence.repositories;

import com.djeno.lab1.persistence.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
