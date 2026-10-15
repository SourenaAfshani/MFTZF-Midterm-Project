import model.Customer;
import model.Order;
import model.OrderItem;
import model.Product;
import service.CustomerService;
import service.OrderService;
import service.ProductService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        ProductService ps=new ProductService();
        CustomerService cs=new CustomerService();
        OrderService os=new OrderService(cs,ps);
        MidTerm:
        while(true){
            Menu();
            int inputMenu=input.nextInt();
            switch (inputMenu){
                case 1:
                    ProductMenu();
                    int ProductMenuInput=input.nextInt();
                    switch(ProductMenuInput){
                        case 1 :
                            Product[] products=ps.ShowAllProducts();
                            for(int i=0;i< products.length;i++){
                                System.out.println(
                                        products[i].getId() + "  " +
                                                products[i].getName() + "  " +
                                                products[i].getCategory() + "  " +
                                                products[i].getPrice() + "  " +
                                                products[i].getQuantity()
                                );
                            }
                            break;
                        case 2:
                            System.out.println("ایدی کالا را وارد کنید");
                            int productFindId=input.nextInt();
                            Product pr=ps.FindProductById(productFindId);
                            System.out.println(pr.getId()+"   "+
                                    pr.getName()+"   "+
                                    pr.getQuantity()+"   "+
                                    pr.getPrice()+"   "+
                                    pr.getCategory());
                            break ;
                        case 3:
                            System.out.println("ایدی کالا خود را وارد کنید");
                            int productAddID=input.nextInt();
                            input.nextLine();
                            System.out.println("نام کالا را وارد کنید");
                            String productAddName=input.nextLine();
                            System.out.println("دسته بندی کالا را وارد کنید");
                            String productAddCategory=input.nextLine();
                            System.out.println("قیمت کالا را وارد کنید");
                            double productAddPrice=input.nextDouble();
                            System.out.println("تعداد موجودی کالا را وارد کنید");
                            int productAddQuantity=input.nextInt();
                            Product pro=new Product(productAddID
                                    ,productAddName
                                    ,productAddCategory
                                    ,productAddPrice,productAddQuantity);
                            ps.addProduct(pro);
                            break;
                        case 4:
                            System.out.println("آیدی کالا را وارد کنید");
                            int productEditId=input.nextInt();
                            ps.FindProductById(productEditId);
                            input.nextLine();
                            System.out.print("نام جدید کالا را وارد کنی");
                            String productEditName = input.nextLine();
                            System.out.print("دسته بندی جدید کالا را وارد کنید ");
                            String productEditCategory = input.nextLine();
                            System.out.print("قیمت جدید را وارد کنید");
                            double productEditPrice = input.nextDouble();
                            System.out.print("تعداد موجودی جدید را وارد کنید");
                            int productEditQuantity = input.nextInt();
                            Product productEdit=new Product(productEditId
                                    ,productEditName
                                    ,productEditCategory
                                    ,productEditPrice,productEditQuantity);
                            ps.editProduct(productEdit);
                            break;
                        case 5:
                            System.out.println("آیدی کالا را وارد کنید");
                            int productRemoveId=input.nextInt();
                            Product productRemove=ps.FindProductById(productRemoveId);
                            ps.removeProduct(productRemove);
                            break;
                        case 6:
                            break;
                        default:
                            System.out.println("ورودی اشتباه");

                    }
                    break;
                case 2:
                    CustomerMenu();
                    int CustomerMenuInput=input.nextInt();
                    switch (CustomerMenuInput){
                        case 1:
                            Customer[] customers= cs.ShowAllCustomers();
                            for(int i=0;i<customers.length;i++){
                                System.out.println(
                                        customers[i].getId() + "  " +
                                                customers[i].getName() + "  " +
                                                customers[i].getPhoneNumber() + "  ");
                            }
                            break;
                        case 2:
                            System.out.println("ایدی مشتری خود را وارد کنید");
                            int customerAddID=input.nextInt();
                            input.nextLine();
                            System.out.println("نام مشتری را وارد کنید");
                            String customerAddName=input.nextLine();
                            System.out.println("شماره موبایل مشتری را وارد کنید");
                            String customerAddPhone=input.nextLine();
                            Customer csAdd=new Customer(customerAddID,customerAddName,customerAddPhone);
                            cs.addCustomer(csAdd);
                            break;
                        case 3:
                            System.out.println("آیدی کاربر را وارد کنید");
                            int customerSearchId=input.nextInt();
                            cs.FindCustomerById(customerSearchId);
                            break;
                        case 4:
                            System.out.println("آیدی مشتری را وارد کنید");
                            int customerEditId=input.nextInt();
                            cs.FindCustomerById(customerEditId);
                            input.nextLine();
                            System.out.println("نام جدید را وارد کنید");
                            String customerEditName=input.nextLine();
                            System.out.println("شماره تلفن جدید را وارد کنید");
                            String customerEditPhoneNumber=input.nextLine();
                            Customer csEdit=new Customer(customerEditId,customerEditName,customerEditPhoneNumber);
                            cs.editCustomer(csEdit);
                            break;
                        case 5:
                            System.out.println("آیدی مشتری را وارد کنید");
                            int customerRemoveId=input.nextInt();
                            Customer csRemove=cs.FindCustomerById(customerRemoveId);
                            cs.removeCustomer(csRemove);
                            break;
                        case 6:
                            break;
                        default:
                            System.out.println("ورودی اشتباه");

                    }
                    break;
                case 3:
                    OrderMenu();
                    int OrderMenuInput=input.nextInt();
                    switch (OrderMenuInput){
                        case 1 :
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

                            for (int i = 0; i < count; i++) {
                                actualItems[i] = items[i];
                            }
                            os.NewOrder(orderAddId,orderCustomerID,actualItems);
                            break;
                        case 2:
                            System.out.println("آیدی سفارش را وارد کنید");
                            int orderSearchId=input.nextInt();
                            Order order=os.OrderSearch(orderSearchId);
                            int customerIdSearch=order.getCustomerId();
                            double totalPriceSearch=order.getTotalPrice();
                            String statusOrderSearch=order.getStatus();
                            System.out.println(customerIdSearch+"    "+
                                    totalPriceSearch+"    "+statusOrderSearch+"    ");
                            break;
                        case 3:
                            System.out.println("آیدی مشتری مورد نظر را وارد کنید");
                            int OrderSearchCustomerId=input.nextInt();
                            Order[] customerorder=os.ShowCustomerOrder(OrderSearchCustomerId);
                            for(int i=0;i<customerorder.length;i++){
                                Order csorder=customerorder[i];
                                System.out.println(csorder.getId()+"     "+
                                        csorder.getStatus()+"     "+
                                        csorder.getTotalPrice());
                            }
                            break;
                        case 4:
                            System.out.println("آیدی سفازش را وارد کنید");
                            int OrderIdStatusCancel=input.nextInt();
                            os.CancelOrder(OrderIdStatusCancel);
                            break;
                        case 5:
                            System.out.println("آیدی سفازش را وارد کنید");
                            int OrderIdStatusComplete=input.nextInt();
                            os.CompleteOrder(OrderIdStatusComplete);
                            break;
                        case 6:
                            break;
                        default:
                            System.out.println("ورودی اشتباه");

                    }
                    break;
                case 4:
                    ReportMenu();
                    int ReportMenuInput=input.nextInt();
                    switch (ReportMenuInput){
                        case 1:
                            Product[] lowstockproducts=ps.ShowLowStockProducts();
                            for(int i=0;i<lowstockproducts.length;i++){
                                Product LowStockProduct=lowstockproducts[i];
                                System.out.println(LowStockProduct.getId()+"   "+
                                        LowStockProduct.getName()+"    "+
                                        LowStockProduct.getCategory()+"   "+
                                        LowStockProduct.getPrice()+"    "+
                                        LowStockProduct.getQuantity());
                            }
                            break;
                        case 2:
                            Product[] nostockproducts=ps.ShowOutOfStockProducts();
                            for(int i=0;i<nostockproducts.length;i++){
                                Product OutOfStockProduct=nostockproducts[i];
                                System.out.println(OutOfStockProduct.getId()+"   "+
                                        OutOfStockProduct.getName()+"    "+
                                        OutOfStockProduct.getCategory()+"   "+
                                        OutOfStockProduct.getPrice()+"    "+
                                        OutOfStockProduct.getQuantity());
                            }
                            break;
                        case 3:
                            System.out.println(os.CalculateTotalSale());
                            break;
                        case 4:
                            break;
                        default:
                            System.out.println("ورودی اشتباه");

                    }
                    break;
                case 5:
                    break MidTerm;

            }


        }

    }
    static void Menu(){
        System.out.println("===================================");
        System.out.println("      STORE MANAGEMENT SYSTEM      ");
        System.out.println("===================================");

        System.out.println("1. Product Management");
        System.out.println("2. Customer Management");
        System.out.println("3. Order Management");
        System.out.println("4. Reports");
        System.out.println("5. Exit");
    }
    static void ProductMenu(){
        System.out.println("===== PRODUCT MANAGEMENT =====");
        System.out.println("1. Show All Products");
        System.out.println("2. Search Product");
        System.out.println("3. Add Product");
        System.out.println("4. Edit Product");
        System.out.println("5. Delete Product");
        System.out.println("6. Back to Main Menu");
    }
    static void CustomerMenu(){
        System.out.println("============CUSTOMER MANAGEMENT=============");
        System.out.println("1. Show All Customers");
        System.out.println("2. Add Customer");
        System.out.println("3. Search Customer");
        System.out.println("4. Edit Customer");
        System.out.println("5. Delete Customer");
        System.out.println("6. Back to Main Menu");
    }
    static void OrderMenu(){
        System.out.println("===================ORDER MANAGEMENT================");
        System.out.println("1. Create New Order");
        System.out.println("2. Search Order by ID");
        System.out.println("3. Show Customer Orders");
        System.out.println("4. Cancel Order");
        System.out.println("5. Complete Order");
        System.out.println("6. Back to Main Menu");
    }
    static void ReportMenu(){
        System.out.println("=================REPORTS MENU================");
        System.out.println("1. Low Stock Products");
        System.out.println("2. Out of Stock Products");
        System.out.println("3. Total Sales");
        System.out.println("4. Back to Main Menu");
    }

}