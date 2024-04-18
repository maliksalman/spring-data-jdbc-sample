package com.samples.springdatajdbc.orders;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/{orderId}")
    public Optional<Order> getOrder(@PathVariable UUID orderId) {
        return service.findById(orderId);
    }

    @PostMapping("/{customerId}")
    public Order createOrder(@PathVariable("customerId") UUID customerId) {
        return service.create(customerId);
    }

    @PutMapping("/{orderId}/{productId}/{quantity}")
    public Order addItem(@PathVariable("orderId") UUID orderId,
                         @PathVariable("productId") UUID productId,
                         @PathVariable("quantity") int quantity) {
        return service.addItem(orderId, productId, quantity);
    }
}
