package com.fate.userservice.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private Long id;

    @Column(length = 20)
    private String name;
    private String email;
    private String about;

    @Transient
    private List<Rating> ratingList = new ArrayList<>();

}
