package com.driver;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepository {
    Map<String,Order> OrderDb = new HashMap<>(); // string id will be its primary key

    public void addOrder(Order order) {
        OrderDb.put(order.getId(),order);
    }

    public Order getOrderById(String orderId) {
        System.out.print(orderId+" "+"in repo");
        return OrderDb.get(orderId);
    }
}
