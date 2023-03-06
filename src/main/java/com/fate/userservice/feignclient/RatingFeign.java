package com.fate.userservice.feignclient;

import com.fate.userservice.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
@Service
@FeignClient("rating-service")
public interface RatingFeign {

    @PostMapping("/api/create")
    ResponseEntity<Rating> createRating(Rating rating);

    @PutMapping("/api/update/{ratingId}")
    ResponseEntity<Rating> updateRating(@PathVariable("ratingId") Long ratingId, Rating rating);

    @DeleteMapping("/api/delete/{ratingId}")
    void deleteRating(@PathVariable("ratingId") Long ratingId);
}
