package com.rest.CLI;

import java.util.Scanner;
import com.rest.models.Bill;
import com.rest.models.OrderItem;
import com.rest.models.Table;
import com.rest.services.BillServices;
import com.rest.services.Payment;
import com.rest.services.CashPayment;
import com.rest.services.UpiPayment;
import com.rest.services.OrderServices;
import com.rest.services.TableServices;

public class PaymentCli {
    private final Scanner sc;
    private final OrderServices orderService;
    private final TableServices tableService;
    private final BillServices billService;

    public PaymentCli(Scanner sc, OrderServices orderService,
                      TableServices tableService,
                      BillServices billService) {
        this.sc = sc;
        this.orderService = orderService;
        this.tableService = tableService;
        this.billService = billService;
    }

    public void start() {
        System.out.println("\n--- Tables with Unpaid Bills ---");

        boolean found = false;

        for (Table table : tableService.getAll()) {
            Bill bill = billService.getUnpaidBill(table.getId());

            if (bill != null) {
                System.out.println("Table ID: " + table.getId()
                        + " | Bill ID: " + bill.getId()
                        + " | Amount: Rs. " + bill.getTotal());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No unpaid bills available.");
            return;
        }

        System.out.print("Enter Table ID for payment: ");
        int tableId = sc.nextInt();

        Bill bill = billService.getUnpaidBill(tableId);

        if (bill == null) {
            System.out.println("No unpaid bill found for this table.");
            return;
        }

        System.out.println("\n--- Saved Bill ---");
        System.out.println("Bill ID: " + bill.getId());
        System.out.println("Table ID: " + bill.getTableId());

        for (OrderItem item : bill.getItems()) {
            System.out.println(item.getItemName()
                    + " x " + item.getQuantity()
                    + " = Rs. " + item.getTotal());
        }

        double total = bill.getTotal();
        System.out.println("Total: Rs. " + total);

        System.out.println("\n1. Cash");
        System.out.println("2. UPI");
        System.out.println("0. Cancel");
        System.out.print("Choose payment method: ");
        int choice = sc.nextInt();

        Payment payment;

        if (choice == 1) {
            payment = new CashPayment(sc);
        } else if (choice == 2) {
            payment = new UpiPayment(sc);
        } else {
            System.out.println("Payment cancelled.");
            return;
        }

        if (payment.pay(total)) {
            bill.markPaid();
            orderService.closeOrder(tableId);

            for (Table table : tableService.getAll()) {
                if (table.getId() == tableId) {
                    table.freeNow();
                    break;
                }
            }

            System.out.println("Payment successful!");
            System.out.println("Bill saved as paid.");
            System.out.println("Order closed. Table is now free.");
        } else {
            System.out.println("Payment unsuccessful.");
            System.out.println("Bill and order remain pending.");
        }
    }
}