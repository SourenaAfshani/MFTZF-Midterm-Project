package view;

import java.util.Scanner;

public class Menu {

    private final Scanner input = new Scanner(System.in);

    public void run() {
        mainMenu:
        while (true) {
            System.out.println("""
                    ===================================================
                                Store Management System
                    ===================================================
                    1. Product
                    2. Customer
                    3. Order
                    4. Reports
                    0. exit
                    """);
            System.out.println("choose a number: ");
            int choice = input.nextInt();
            switch (choice) {
                case 1 -> productMenu();
                case 2 -> customerMenu();
                case 3 -> orderMenu();
                case 4 -> reportsMenu();
                case 0 -> {
                    break mainMenu;
                }
                default -> System.out.println("wrong input.");
            }
        }
    }

    private void reportsMenu() {
        ReportsMenu reportsMenu = new ReportsMenu();
        reportsMenu.run();
    }

    private void orderMenu() {
        OrderMenu orderMenu = new OrderMenu();
        orderMenu.run();
    }

    private void customerMenu() {
        CustomerMenu customerMenu = new CustomerMenu();
        customerMenu.run();
    }

    private void productMenu() {
        ProductMenu productMenu = new ProductMenu();
        productMenu.run();
    }
}
