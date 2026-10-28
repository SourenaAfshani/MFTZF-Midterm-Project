package view;

import model.Product;
import service.CustomerService;
import service.ProductService;

import java.util.Scanner;

public class ProductMenu {
    Scanner input= new Scanner(System.in);
    ProductService ps=new ProductService();
    public void run(){
        ProductMenu:
        while (true){
            System.out.println("""
                    ===================================================
                                    Product Management
                    ===================================================
                    1. Show All Products
                    2. Search Product
                    3. Add Product
                    4. Edit Product
                    5. Delete Product
                    0. Exit
                    """);
            int Choice=input.nextInt();
            switch (Choice) {
                case 1 -> ShowAllProducts();
                case 2 -> SearchProduct();
                case 3 -> AddProduct();
                case 4 -> EditProduct();
                case 5 -> DeleteProduct();
                case 0 -> {
                    break ProductMenu;
                }
                default -> System.out.println("wrong input.");
            }
        }
    }
    private void ShowAllProducts(){
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
    }
    private void SearchProduct(){
        System.out.println("ایدی کالا را وارد کنید");
        int productFindId=input.nextInt();
        Product pr=ps.FindProductById(productFindId);
        System.out.println(pr.getId()+"   "+
                pr.getName()+"   "+
                pr.getQuantity()+"   "+
                pr.getPrice()+"   "+
                pr.getCategory());
    }
    private void AddProduct(){
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
    }
    private void EditProduct(){
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
    }
    private void DeleteProduct(){
        System.out.println("آیدی کالا را وارد کنید");
        int productRemoveId=input.nextInt();
        Product productRemove=ps.FindProductById(productRemoveId);
        ps.removeProduct(productRemove);
    }

}
