package com.samples.springdatajdbc.heroes;

import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public record Hero(

        @Id Long id,
        String name,
        int age) {

}
