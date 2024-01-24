package com.samples.springdatajpa;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("heroes")
@Builder
public record Hero(

    @Id
    Long id,
    String name,
    int age
) {

}
