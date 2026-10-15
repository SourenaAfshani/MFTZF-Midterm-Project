package service;

import model.OrderItem;
import model.Product;

public interface ProductServiceImpl {
    public Product[] ShowAllProducts();
    public Product  FindProductById(int id);
    public void addProduct(Product pr);
    public void removeProduct(Product pr);
    public void editProduct(Product pr);
    public void QuantityReducer(Product Pr,int Quantity);
    public Product[] ShowLowStockProducts();
    public Product[] ShowOutOfStockProducts();




}
