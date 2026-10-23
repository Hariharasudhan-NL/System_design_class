package com.rest.models;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private int id;
    private int tableId;
    private List<OrderItem> items;
    private boolean paid;

    public Bill(int id, int tableId, Order order) {
        this.id = id;
        this.tableId = tableId;
        this.items = new ArrayList<>();
        this.paid = false;

        // Save a snapshot of the order items
        for (OrderItem item : order.getItems()) {
            items.add(new OrderItem(
                item.getItemName(),
                item.getQuantity(),
                item.getPrice()
            ));
        }
    }

    public int getId() {
        return id;
    }

    public int getTableId() {
        return tableId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public boolean isPaid() {
        return paid;
    }

    public void markPaid() {
        paid = true;
    }

    public double getTotal() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getTotal();
        }

        return total;
    }
}