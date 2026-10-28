package view;

import model.Order;
import model.OrderItem;
import service.CustomerService;
import service.OrderService;
import service.ProductService;

import java.util.Scanner;

public class OrderMenu {
    Scanner input= new Scanner(System.in);
    CustomerService cs=new CustomerService();
    ProductService ps=new ProductService();
    OrderService os=new OrderService(cs,ps);
    public void run(){
        OrderMenu:
        while (true){
            System.out.println("""
                    ===================================================
                                    Order Management
                    ===================================================
                    1. Create New Order
                    2. Search Order by ID
                    3. Show Customer Orders
                    4. Cancel Order
                    5. Complete Order
                    0. Exit
                    """);
            int Choice=input.nextInt();
            switch (Choice) {
                case 1 -> CreateOrder();
                case 2 -> SearchOrderById();
                case 3 -> ShowCustomerOrders();
                case 4 -> CancelOrder();
                case 5 -> CompleteOrder();
                case 0 -> {
                    break OrderMenu;
                }
                default -> System.out.println("wrong input.");
            }
        }
    }
    private void CreateOrder(){
        OrderItem[] items = new OrderItem[10];
        int count = 0;
        System.out.println("شماره سفارش را وارد کنید");
        int orderAddId=input.nextInt();
        System.out.println("ایدی خریدار را وارد کنید");
        int orderCustomerID=input.nextInt();

        for (int i = 0; i < 10; i++) {
            System.out.print("آیدی کالا را وارد کنید ");
            int orderAddProductId = input.nextInt();
            System.out.print("تعداد را وارد کنید");
            int quantity = input.nextInt();
            System.out.println("مبلغ کالا را وارد کنید");
            double orderAddUnitPrice=input.nextDouble();
            items[i] = new OrderItem(orderAddId, orderAddProductId, quantity, orderAddUnitPrice);
            count++;
            if (i == 9) {
                System.out.println("هر سفارش میتواند شامل 10 کالا باشد");
                break;
            }
            System.out.print("Add another product? (y/n): ");
            String answer = input.next();
            if (answer.equals("n") || answer.equals("N")) {
                break;
            }
        }
        OrderItem[] actualItems = new OrderItem[count];

        System.arraycopy(items, 0, actualItems, 0, count);
        os.NewOrder(orderAddId,orderCustomerID,actualItems);

    }
    private void SearchOrderById(){
        System.out.println("آیدی سفارش را وارد کنید");
        int orderSearchId=input.nextInt();
        Order order = os.OrderSearch(orderSearchId);
        int customerIdSearch=order.getCustomerId();
        double totalPriceSearch=order.getTotalPrice();
        String statusOrderSearch=order.getStatus();
        System.out.println(customerIdSearch+"    "+
                totalPriceSearch+"    "+statusOrderSearch+"    ");
    }
    private void ShowCustomerOrders(){
        System.out.println("آیدی مشتری مورد نظر را وارد کنید");
        int OrderSearchCustomerId=input.nextInt();
        Order[] customerorder=os.ShowCustomerOrder(OrderSearchCustomerId);
        for(int i=0;i<customerorder.length;i++){
            Order csorder=customerorder[i];
            System.out.println(csorder.getId()+"     "+
                    csorder.getStatus()+"     "+
                    csorder.getTotalPrice());
        }
    }
    private void CancelOrder(){
        System.out.println("آیدی سفازش را وارد کنید");
        int OrderIdStatusCancel=input.nextInt();
        os.CancelOrder(OrderIdStatusCancel);
    }
    private void CompleteOrder(){
        System.out.println("آیدی سفازش را وارد کنید");
        try {
            int OrderIdStatusComplete = input.nextInt();
            os.CompleteOrder(OrderIdStatusComplete);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }


}
