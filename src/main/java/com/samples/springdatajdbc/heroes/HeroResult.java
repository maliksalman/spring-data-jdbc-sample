package com.samples.springdatajdbc.heroes;

import java.time.LocalDateTime;

public record HeroResult(
        Hero hero,
        String host,
        LocalDateTime time) {
}
