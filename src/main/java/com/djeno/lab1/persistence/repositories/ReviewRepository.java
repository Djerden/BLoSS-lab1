package com.djeno.lab1.persistence.repositories;

import com.djeno.lab1.persistence.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
