
package com.rest.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int tableId;
    private final List<OrderItem> items = new ArrayList<>();

    public Order(int id, int tableId) {
        this.id = id;
        this.tableId = tableId;
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

    public void addItem(String name, int quantity, double price) {
        for (OrderItem item : items) {
            if (item.getItemName().equals(name)) {
                item.addQuantity(quantity);
                return;
            }
        }

        items.add(new OrderItem(name, quantity, price));
    }

    public double getTotal() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getTotal();
        }

        return total;
    }
}
