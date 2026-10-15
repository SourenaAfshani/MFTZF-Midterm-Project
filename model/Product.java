package model;

import exception.InsufficientStockException;
import exception.WrongNumberException;

public class Product{

    private int id;
    private String Name;
    private String Category;
    private double Price;
    private int quantity;

    public Product(int id, String name, String Category, double Price, int quantity) {
        this.id = id;
        this.Name = name;
        this.Category = Category;
        this.Price = Price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    void ChangePrice(double newprice){
        if(newprice<0) {
            throw new WrongNumberException("مقدار مبلغ جدید باید بزرگتر از 0 باشد");
        }
        Price=newprice;
    }
    void quantityRaiser(int newquantity){
        if(newquantity<0){
            throw new WrongNumberException("مقدار موجودی جدید نباید کمتر از 0 باشد");
        }
        quantity=newquantity;
    }
    void quantityReductioner(int newquantity){
        if(newquantity<0){
            throw new WrongNumberException("مقدار موجودی جدید نباید کمتر از 0 باشد");
        }
        if(quantity==0){
            throw new InsufficientStockException("موجودی کافی نیست");
        }
        quantity=newquantity;
    }
}


