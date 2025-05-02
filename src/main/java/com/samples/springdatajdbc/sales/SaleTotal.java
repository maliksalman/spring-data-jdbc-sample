package com.samples.springdatajdbc.sales;

import java.time.LocalDate;

public interface SaleTotal {
    LocalDate getSaleDate();
    int getTotal();
}
