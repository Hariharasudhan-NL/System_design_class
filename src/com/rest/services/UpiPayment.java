package com.rest.services;

import java.util.Scanner;

public class UpiPayment implements Payment {

    private Scanner sc;

    public UpiPayment(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Payment Method: UPI");
        System.out.println("Amount to Pay: Rs. " + amount);
        System.out.print("Confirm UPI payment received? (1.Yes / 0.No): ");

        int choice = sc.nextInt();

        if (choice == 1) {
            System.out.println("UPI payment confirmed.");
            return true;
        }

        System.out.println("Payment cancelled.");
        return false;
    }
}