package com.samples.springdatajdbc.sales;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SalesController {

    private final SaleRepository repo;

    @PutMapping("/{id}/{amount}")
    public Sale update(@PathVariable("id") Long id, @PathVariable("amount") int amount) {
        return repo.findById(id)
                .map(sale -> repo.save(sale.add(amount)))
                .get();
    }

    @PostMapping("/{date}/{amount}")
    public Sale add(@PathVariable("date") LocalDate date, @PathVariable("amount") int amount) {
        return repo.save(Sale.builder()
                        .total(amount)
                        .saleDate(date)
                        .dayOfWeek(date.getDayOfWeek().name().toUpperCase().substring(0, 3))
                .build());
    }

        @GetMapping
    public Iterable<Sale> all() {
        return repo.findAll();
    }

    @GetMapping("/mondays")
    public List<Sale> mondaysList() {
        return repo.findSalesByDayOfWeekOrderBySaleDate("MON");
    }

    @GetMapping("/mondays/sum")
    public int mondays() {
        return repo.getSalesForDayOfWeek("MON");
    }

    @GetMapping("/partial/{date}")
    public SaleTotal partialSale(@PathVariable("date") LocalDate date) {
        return repo.findFirstSaleBySaleDate(date);
    }
}
