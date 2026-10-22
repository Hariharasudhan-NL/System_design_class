
package com.rest.services;

import java.util.List;
import com.rest.models.Order;
import com.rest.repository.OrderRepository;

public class OrderServices {
    private final OrderRepository repository;

    public OrderServices() {
        repository = new OrderRepository();
    }

    public Order getOrCreateOrder(int tableId) {
        Order order = repository.findByTable(tableId);

        if (order == null) {
            order = repository.create(tableId);
        }

        return order;
    }

    public void addItem(int tableId, String name,
                        int quantity, double price) {
        Order order = getOrCreateOrder(tableId);
        order.addItem(name, quantity, price);
    }

    public Order getOrder(int tableId) {
        return repository.findByTable(tableId);
    }

    public List<Order> getAll() {
        return repository.show();
    }

    public void closeOrder(int tableId) {
        Order order = repository.findByTable(tableId);

        if (order != null) {
            repository.close(order);
        }
    }
}
