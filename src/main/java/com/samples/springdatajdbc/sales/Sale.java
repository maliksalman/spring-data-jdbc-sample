package com.samples.springdatajdbc.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDate;

/**
 * The reason why this a class and not a record is so that spring-data repository
 * support can create projections from the entity. It can't do that with records.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sale {


    @Id Long id;
    @Version int version;
    int total;
    LocalDate saleDate;
    String dayOfWeek;

    public Sale add(int amount) {
        return new Sale(id, version, total+amount, saleDate, dayOfWeek);
    }
}
