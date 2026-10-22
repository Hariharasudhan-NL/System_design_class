
package com.rest.CLI;

import java.util.Scanner;

import com.rest.models.Order;
import com.rest.models.OrderItem;
import com.rest.models.Table;
import com.rest.services.OrderServices;
import com.rest.services.TableServices;

public class BillCli {
    private final OrderServices orderService;
    private final TableServices tableService;
    private final Scanner sc = new Scanner(System.in);

    public BillCli(OrderServices orderService,
                   TableServices tableService) {
        this.orderService = orderService;
        this.tableService = tableService;
    }

    public void start() {
        int tableId;

        System.out.println("\nBill Management");

        System.out.print("Enter table ID: ");
        tableId = sc.nextInt();

        Table selectedTable = null;

        for (Table t : tableService.getAll()) {
            if (t.getId() == tableId) {
                selectedTable = t;
                break;
            }
        }

        if (selectedTable == null) {
            System.out.println("Table not found!");
            return;
        }

        Order order = orderService.getOrder(tableId);

        if (order == null || order.getItems().isEmpty()) {
            System.out.println("No pending order for this table!");
            return;
        }

        System.out.println("\n========== BILL ==========");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Table ID: " + tableId);

        for (OrderItem item : order.getItems()) {
            System.out.println(
                item.getItemName() + " x " + item.getQuantity()
                + " = Rs." + item.getTotal()
            );
        }

        System.out.println("--------------------------");
        System.out.println("Total Amount: Rs." + order.getTotal());
        System.out.println("==========================");

        System.out.print("Confirm payment? (1.Yes / 0.No): ");
        int confirm = sc.nextInt();

        if (confirm == 1) {
            orderService.closeOrder(tableId);
            selectedTable.freeNow();

            System.out.println("Payment completed!");
            System.out.println("Table " + tableId + " is now free.");
        } else {
            System.out.println("Bill kept pending.");
            System.out.println("Table remains occupied.");
        }
    }
}
