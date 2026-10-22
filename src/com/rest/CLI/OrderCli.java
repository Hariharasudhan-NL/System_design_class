
package com.rest.CLI;

import java.util.List;
import java.util.Scanner;

import com.rest.models.Menu;
import com.rest.models.Order;
import com.rest.models.OrderItem;
import com.rest.models.Table;
import com.rest.services.MenuServices;
import com.rest.services.OrderServices;
import com.rest.services.TableServices;

public class OrderCli {
    private final OrderServices orderService;
    private final MenuServices menuService;
    private final TableServices tableService;
    private final Scanner sc = new Scanner(System.in);

    public OrderCli(OrderServices orderService,
                    MenuServices menuService,
                    TableServices tableService) {
        this.orderService = orderService;
        this.menuService = menuService;
        this.tableService = tableService;
    }

    public void start() {
        int choice;

        do {
            System.out.println("\nOrder Management");
            System.out.println("1. Add Items to Table");
            System.out.println("2. View Table Order");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addItems();
                    break;

                case 2:
                    System.out.print("Enter table ID: ");
                    int tableId = sc.nextInt();
                    showOrder(tableId);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private void addItems() {
        System.out.println("\nAvailable Tables");

        for (Table t : tableService.getAll()) {
            System.out.println(
                "Table " + t.getId() + " - " + t.getStatus()
            );
        }

        System.out.print("Enter table ID: ");
        int tableId = sc.nextInt();

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

        List<Menu> menu = menuService.getAll();

        int more;

        do {
            System.out.println("\nAvailable Menu");

            for (Menu m : menu) {
                System.out.println(
                    m.getId() + ". " + m.getName()
                    + " - Rs." + m.getPrice()
                );
            }

            System.out.print("Enter item ID: ");
            int itemId = sc.nextInt();

            Menu selectedItem = null;

            for (Menu m : menu) {
                if (m.getId() == itemId) {
                    selectedItem = m;
                    break;
                }
            }

            if (selectedItem == null) {
                System.out.println("Invalid item ID!");
            } else {
                System.out.print("Enter quantity: ");
                int quantity = sc.nextInt();

                if (quantity <= 0) {
                    System.out.println("Invalid quantity!");
                } else {
                    orderService.addItem(
                        tableId,
                        selectedItem.getName(),
                        quantity,
                        selectedItem.getPrice()
                    );

                    selectedTable.booking();

                    System.out.println("Item added to Table " + tableId);
                }
            }

            System.out.print("Add another item? (1.Yes / 0.No): ");
            more = sc.nextInt();

        } while (more == 1);

        showOrder(tableId);
    }

    private void showOrder(int tableId) {
        Order order = orderService.getOrder(tableId);

        if (order == null || order.getItems().isEmpty()) {
            System.out.println("No open order for this table.");
            return;
        }

        System.out.println("\n----- CURRENT ORDER -----");
        System.out.println("Table ID: " + tableId);

        for (OrderItem item : order.getItems()) {
            System.out.println(
                item.getItemName() + " x " + item.getQuantity()
                + " = Rs." + item.getTotal()
            );
        }

        System.out.println("-------------------------");
        System.out.println("Total: Rs." + order.getTotal());
    }
}
