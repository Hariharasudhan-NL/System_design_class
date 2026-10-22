
package com.rest.repository;

import java.util.ArrayList;
import java.util.List;
import com.rest.models.Order;

public class OrderRepository {
    private final List<Order> orders = new ArrayList<>();

    public Order findByTable(int tableId) {
        for (Order order : orders) {
            if (order.getTableId() == tableId) {
                return order;
            }
        }
        return null;
    }

    public Order create(int tableId) {
        Order order = new Order(orders.size() + 1, tableId);
        orders.add(order);
        return order;
    }

    public List<Order> show() {
        return orders;
    }

    public void close(Order order) {
        orders.remove(order);
    }
}
