package view;

import exception.CustomerNotFoundException;
import model.Customer;
import service.CustomerService;

import java.util.Scanner;

public class CustomerMenu {
    Scanner input= new Scanner(System.in);
    CustomerService cs=new CustomerService();
    public void run(){
        customerMenu:
       while (true){
           System.out.println("""
                    ===================================================
                                    Customer Management
                    ===================================================
                    1. ShowAllCustomer
                    2. Add Customer
                    3. Search Customer
                    4. Edit Customer
                    5. Delete Customer
                    0. Exit
                    """);
           int Choice=input.nextInt();
           switch (Choice) {
               case 1 -> cs.ShowAllCustomers();
               case 2 -> addCustomerMenu();
               case 3 -> SearchCustomerMenu();
               case 4 -> EditCustomerMenu();
               case 5 -> DeleteCustomerMenu();
               case 0 -> {
                   break customerMenu;
               }
               default -> System.out.println("wrong input.");
       }
    }
}

    private void addCustomerMenu() {
        System.out.println("ایدی مشتری خود را وارد کنید");
        int customerAddID=input.nextInt();
        input.nextLine();
        System.out.println("نام مشتری را وارد کنید");
        String customerAddName=input.nextLine();
        System.out.println("شماره موبایل مشتری را وارد کنید");
        String customerAddPhone=input.nextLine();
        Customer csAdd=new Customer(customerAddID,customerAddName,customerAddPhone);
        cs.addCustomer(csAdd);
    }
    private void SearchCustomerMenu(){
        System.out.println("آیدی کاربر را وارد کنید");
        int customerSearchId=input.nextInt();
        cs.FindCustomerById(customerSearchId);
    }
    private void EditCustomerMenu(){
        System.out.println("آیدی مشتری را وارد کنید");
        int customerEditId=input.nextInt();
        try {
            cs.FindCustomerById(customerEditId);
        }
        catch(CustomerNotFoundException e ){
            System.out.println(e.getMessage());
        }
        input.nextLine();
        System.out.println("نام جدید را وارد کنید");
        String customerEditName=input.nextLine();
        System.out.println("شماره تلفن جدید را وارد کنید");
        String customerEditPhoneNumber=input.nextLine();
        Customer csEdit=new Customer(customerEditId,customerEditName,customerEditPhoneNumber);
        cs.editCustomer(csEdit);
    }
    private void DeleteCustomerMenu(){
        System.out.println("آیدی مشتری را وارد کنید");
        int customerRemoveId=input.nextInt();
        Customer csRemove=cs.FindCustomerById(customerRemoveId);
        cs.removeCustomer(csRemove);
    }


}
