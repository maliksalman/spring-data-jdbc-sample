package com.samples.springdatajdbc.sales;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends CrudRepository<Sale, Long> {

    /**
     * This query will be sent exactly to the DB, no table/column
     * name translations will happen. This is different compared
     * to spring-data-jpa where the translation does happen. If
     * the DB is case-sensitive, the case will matter as well.
     */
    @Query("select sum(s.total) from sale s where s.day_of_week = :dayOfWeek")
    int getSalesForDayOfWeek(@Param("dayOfWeek") String dayOfWeek);

    /**
     * Example of well named method that spring-data-jdbc will implement
     */
    List<Sale> findSalesByDayOfWeekOrderByDate(String dayOfWeek);

    /**
     * Example of a projection. The projection is an interface matching
     * exact column names with the entity. Also, the entity has to be a
     * bean with set/get methods and no-args constructor - records not
     * supported.
     */
    SaleTotal findFirstSaleByDate(LocalDate date);
}
