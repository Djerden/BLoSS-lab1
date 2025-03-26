package com.djeno.lab1.controllers;

import com.djeno.lab1.persistence.DTO.review.CreateReviewRequest;
import com.djeno.lab1.persistence.DTO.review.ReviewDTO;
import com.djeno.lab1.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody @Valid CreateReviewRequest request) {
        reviewService.createReview(request);
        return ResponseEntity.ok("Отзыв успешно создан");
    }
}
