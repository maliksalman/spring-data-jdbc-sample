package com.samples.springdatajdbc;

import com.samples.springdatajdbc.heroes.Hero;
import com.samples.springdatajdbc.heroes.HeroRepository;
import com.samples.springdatajdbc.sales.Sale;
import com.samples.springdatajdbc.sales.SaleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SpringDataJDBCSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJDBCSampleApplication.class, args);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventListener(SaleRepository saleRepository, HeroRepository heroRepository) {
        return event -> {

            // clean the heroes
            heroRepository.deleteAll();
            heroRepository.saveAll(List.of(
                    Hero.builder()
                            .name("superman")
                            .age(35)
                            .build(),
                    Hero.builder()
                            .name("batman")
                            .age(45)
                            .build(),
                    Hero.builder()
                            .name("flash")
                            .age(25)
                            .build()
            ));

            // clean the sales
            saleRepository.deleteAll();
            saleRepository.saveAll(List.of(
                    Sale.builder()
                            .saleDate(LocalDate.of(2024, 4, 1))
                            .dayOfWeek("MON").total(100)
                            .build(),
                    Sale.builder()
                            .saleDate(LocalDate.of(2024, 4, 2))
                            .dayOfWeek("TUE").total(200)
                            .build(),
                    Sale.builder()
                            .saleDate(LocalDate.of(2024, 4, 3))
                            .dayOfWeek("WED").total(300)
                            .build()
            ));
        };
    }
}
