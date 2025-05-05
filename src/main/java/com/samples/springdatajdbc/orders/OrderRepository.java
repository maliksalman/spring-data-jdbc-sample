package com.samples.springdatajdbc.orders;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderRepository {

    private final JdbcClient client;

    public OrderRepository(JdbcClient client) {
        this.client = client;
    }

    public Order insert(Order order) {

        UUID orderId = UUID.randomUUID();
        OffsetDateTime timestamp = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);

        client.sql("insert into orders(order_id, customer_id, total_amount, tax, created, updated) values (?, ?, ?, ?, ?, ?)")
                .params(orderId.toString(),
                        order.customerId().toString(),
                        order.totalAmount(),
                        order.tax(),
                        timestamp,
                        timestamp)
                .update();

        return order.toBuilder()
                .orderId(orderId)
                .created(timestamp)
                .updated(timestamp)
                .build();
    }

    public Order update(Order order) {

        OffsetDateTime timestamp = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);

        client.sql("update orders set total_amount=?, tax=?, updated=? where order_id=?")
                .params(order.totalAmount(),
                        order.tax(),
                        timestamp,
                        order.orderId().toString())
                .update();

        return order.toBuilder()
                .updated(timestamp)
                .build();
    }

    public Order.Item insert(Order.Item item) {

        UUID itemId = UUID.randomUUID();
        OffsetDateTime timestamp = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);

        client.sql("insert into order_items(order_id, item_id, product_id, quantity, price, created) values (?, ?, ?, ?, ?, ?)")
                .params(item.orderId().toString(),
                        itemId.toString(),
                        item.productId().toString(),
                        item.quantity(),
                        item.price(),
                        timestamp)
                .update();


        return item.toBuilder()
                .itemId(itemId)
                .created(timestamp)
                .build();
    }

    public Optional<Order> findById(UUID orderId) {

        Optional<Order> order = client.sql("select order_id, customer_id, total_amount, tax, created, updated from orders where order_id=?")
                .params(orderId.toString())
                .query((rs, rowNum) -> Order.builder()
                        .orderId(UUID.fromString(rs.getString("order_id")))
                        .customerId(UUID.fromString(rs.getString("customer_id")))
                        .tax(rs.getInt("tax"))
                        .totalAmount(rs.getInt("total_amount"))
                        .created(OffsetDateTime.ofInstant(rs.getTimestamp("created").toInstant(), ZoneOffset.UTC))
                        .updated(OffsetDateTime.ofInstant(rs.getTimestamp("updated").toInstant(), ZoneOffset.UTC))
                        .build())
                .optional();

        if (order.isPresent()) {
            List<Order.Item> items = client.sql("select item_id, product_id, price, quantity, created from order_items where order_id=? order by created asc")
                    .params(orderId.toString())
                    .query((rs, rowNum) -> Order.Item.builder()
                            .orderId(orderId)
                            .itemId(UUID.fromString(rs.getString("item_id")))
                            .productId(UUID.fromString(rs.getString("product_id")))
                            .quantity(rs.getInt("quantity"))
                            .price(rs.getInt("price"))
                            .created(OffsetDateTime.ofInstant(rs.getTimestamp("created").toInstant(), ZoneOffset.UTC))
                            .build())
                    .list();

            return order.map(o -> o.toBuilder()
                    .items(items)
                    .build());
        }

        return order;
    }
}
