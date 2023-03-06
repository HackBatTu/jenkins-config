package com.fate.userservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {
    @Id
    private Long id;

    private String name;
    private String location;
    private String about;
}
