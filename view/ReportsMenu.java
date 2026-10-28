package view;

import model.Product;
import service.CustomerService;
import service.OrderService;
import service.ProductService;

import java.util.Scanner;

public class ReportsMenu {
    Scanner input= new Scanner(System.in);
    CustomerService cs=new CustomerService();
    ProductService ps=new ProductService();
    OrderService os=new OrderService(cs,ps);
    public void run(){
        ReportMenu:
        while (true){
            System.out.println("""
                    ===================================================
                                         Reports
                    ===================================================
                    1. Low Stock Products
                    2. Out of Stock Products
                    3. Total Sales
                    0. Exit
                    """);
            int Choice=input.nextInt();
            switch (Choice) {
                case 1 -> ShowLowStock();
                case 2 -> ShowOutOfStock();
                case 3 -> TotalSale();
                case 0 -> {
                    break ReportMenu;
                }
                default -> System.out.println("wrong input.");
            }
        }
    }
    private void ShowLowStock(){
        Product[] lowstockproducts=ps.ShowLowStockProducts();
        for(int i=0;i<lowstockproducts.length;i++){
            Product LowStockProduct=lowstockproducts[i];
            System.out.println(LowStockProduct.getId()+"   "+
                    LowStockProduct.getName()+"    "+
                    LowStockProduct.getCategory()+"   "+
                    LowStockProduct.getPrice()+"    "+
                    LowStockProduct.getQuantity());
        }
    }
    private void ShowOutOfStock(){
        Product[] nostockproducts=ps.ShowOutOfStockProducts();
        for(int i=0;i<nostockproducts.length;i++){
            Product OutOfStockProduct=nostockproducts[i];
            System.out.println(OutOfStockProduct.getId()+"   "+
                    OutOfStockProduct.getName()+"    "+
                    OutOfStockProduct.getCategory()+"   "+
                    OutOfStockProduct.getPrice()+"    "+
                    OutOfStockProduct.getQuantity());
        }
    }
    private void TotalSale(){
        System.out.println(os.CalculateTotalSale());
    }

}
