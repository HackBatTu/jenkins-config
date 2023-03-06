package com.fate.userservice.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating{
        @Id
        private Long id;

        private Long userId;
        private Long hotelId;

        private int rating;
        private String feedback;

        private Hotel hotel;
}