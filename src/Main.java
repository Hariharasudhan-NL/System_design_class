
import java.util.Scanner;

import com.rest.CLI.TableCli;
import com.rest.CLI.MenuCli;
import com.rest.CLI.OrderCli;
import com.rest.CLI.BillCli;
import com.rest.CLI.PaymentCli;
import com.rest.services.BillServices;

import com.rest.services.TableServices;
import com.rest.services.MenuServices;
import com.rest.services.OrderServices;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        TableServices tableService = new TableServices();
        MenuServices menuService = new MenuServices();
        OrderServices orderService = new OrderServices();
        
        BillServices billService = new BillServices();

        TableCli tableCli = new TableCli(tableService);
        MenuCli menuCli = new MenuCli(menuService);
        BillCli billCli = new BillCli(sc, orderService, tableService, billService);
        PaymentCli paymentCli = new PaymentCli(sc, orderService, tableService, billService);

        OrderCli orderCli = new OrderCli(
            orderService, menuService, tableService
        );

        
        int choice;

        do {
            System.out.println("\nRestaurant Management");
            System.out.println("1. Table Management");
            System.out.println("2. Menu Management");
            System.out.println("3. Order Management");
            System.out.println("4. Bill Management");
            System.out.println("5. Payment Management");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    tableCli.start();
                    break;

                case 2:
                    menuCli.start();
                    break;

                case 3:
                    orderCli.start();
                    break;

                case 4:
                    billCli.start();
                    break;
                
                case 5:
                    paymentCli.start();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);

        sc.close();
    }
}
