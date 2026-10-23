package com.rest.CLI;

import java.util.Scanner;
import com.rest.models.Order;
import com.rest.models.Table;
import com.rest.services.BillServices;
import com.rest.services.OrderServices;
import com.rest.services.TableServices;

public class BillCli {
    private final Scanner sc;
    private final OrderServices orderService;
    private final TableServices tableService;
    private final BillServices billService;

    public BillCli(Scanner sc, OrderServices orderService,
                   TableServices tableService,
                   BillServices billService) {
        this.sc = sc;
        this.orderService = orderService;
        this.tableService = tableService;
        this.billService = billService;
    }

    public void start() {
        System.out.println("\n--- Tables Ready for Billing ---");

        boolean found = false;

        for (Table table : tableService.getAll()) {
            Order order = orderService.getOrder(table.getId());

            if (order != null && !order.getItems().isEmpty()
                    && billService.getUnpaidBill(table.getId()) == null) {
                System.out.println("Table ID: " + table.getId()
                        + " | Order ID: " + order.getId()
                        + " | Amount: Rs. " + order.getTotal());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tables are ready for billing.");
            return;
        }

        System.out.print("Enter Table ID to generate bill: ");
        int tableId = sc.nextInt();

        Order order = orderService.getOrder(tableId);

        if (order == null || order.getItems().isEmpty()) {
            System.out.println("No active order for this table.");
            return;
        }

        if (billService.getUnpaidBill(tableId) != null) {
            System.out.println("An unpaid bill already exists for this table.");
            return;
        }

        boolean tableExists = false;
        for (Table table : tableService.getAll()) {
            if (table.getId() == tableId) {
                tableExists = true;
                break;
            }
        }

        if (!tableExists) {
            System.out.println("Invalid table ID.");
            return;
        }

        var bill = billService.createBill(tableId, order);

        if (bill == null) {
            System.out.println("Bill could not be generated.");
            return;
        }

        System.out.println("\nBill saved successfully!");
        System.out.println("Bill ID: " + bill.getId());
        System.out.println("Table ID: " + bill.getTableId());
        System.out.println("Total: Rs. " + bill.getTotal());
        System.out.println("Table remains occupied until payment.");
    }
}