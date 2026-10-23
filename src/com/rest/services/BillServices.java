package com.rest.services;

import java.util.List;
import com.rest.models.Bill;
import com.rest.models.Order;
import com.rest.repository.BillRepository;

public class BillServices {
    private final BillRepository repository;

    public BillServices() {
        repository = new BillRepository();
    }

    public Bill createBill(int tableId, Order order) {
        if (repository.hasUnpaidBill(tableId)) {
            return null;
        }

        return repository.create(tableId, order);
    }

    public Bill getUnpaidBill(int tableId) {
        return repository.findUnpaidByTable(tableId);
    }

    public List<Bill> getAllBills() {
        return repository.getAll();
    }
}
