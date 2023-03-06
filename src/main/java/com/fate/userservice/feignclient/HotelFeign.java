package com.fate.userservice.feignclient;

import com.fate.userservice.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("hotel-service")
public interface HotelFeign {

    @GetMapping("/api/findById/{hotelId}")
    Hotel findOne(@PathVariable Long hotelId);
}
