package com.rest.repository;

import java.util.ArrayList;
import java.util.List;
import com.rest.models.Bill;

public class BillRepository {
    private final List<Bill> bills = new ArrayList<>();
    private int nextId = 1;

    public Bill save(Bill bill) {
        bills.add(bill);
        return bill;
    }

    public Bill create(int tableId,
                       com.rest.models.Order order) {
        Bill bill = new Bill(nextId++, tableId, order);
        bills.add(bill);
        return bill;
    }

    public Bill findUnpaidByTable(int tableId) {
        for (Bill bill : bills) {
            if (bill.getTableId() == tableId && !bill.isPaid()) {
                return bill;
            }
        }
        return null;
    }

    public boolean hasUnpaidBill(int tableId) {
        return findUnpaidByTable(tableId) != null;
    }

    public List<Bill> getAll() {
        return bills;
    }
}