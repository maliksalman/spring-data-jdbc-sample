package com.samples.springdatajdbc.orders;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final double taxRate = 0.06;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order create(UUID customerId) {
        return repository.insert(Order.builder()
                .customerId(customerId)
                .build());
    }

    public Optional<Order> findById(UUID orderId) {
        return repository.findById(orderId);
    }

    public Order addItem(UUID orderId, UUID productId, int quantity) {

        // find the order ... will have to deal with missing order in future
        Order order = findById(orderId).get();

        // add the item
        Order.Item item = repository.insert(Order.Item.builder()
                .orderId(orderId)
                .productId(productId)
                .quantity(quantity)
                .price(1099)
                .build());

        // update the order - simple calculations with hard-coded tax-rate
        Order updated = repository.update(order.toBuilder()
                .totalAmount(order.totalAmount() + (item.price() * item.quantity()))
                .tax(order.tax() + (int)(item.price() * item.quantity() * taxRate))
                .build());

        // return the updated order
        return updated.toBuilder()
                .item(item)
                .build();
    }
}
