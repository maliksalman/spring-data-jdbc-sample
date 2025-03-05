package com.samples.springdatajdbc.heroes;

import java.time.LocalDateTime;
import java.util.List;

public record HeroListResult (
    List<Hero> heroes,
    String host,
    LocalDateTime time) {
}
