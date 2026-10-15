package service;

import model.Customer;
import model.Order;
import model.OrderItem;
import model.Product;

public interface OrderServiceImpl {
    Order NewOrder(int OrderId,int CustomerId, OrderItem[] items);
    boolean QuantityCheck(Product pr,OrderItem Or);
    double OrderTotal(Order or);
    void SaveOrder(Order or);
    Order OrderSearch(int OrderId);
    Order[] ShowCustomerOrder(int CustomerId);
    void CancelOrder(int OrderId);
    void CompleteOrder(int OrderId);
    Order[] ShowAllOrders();
    double CalculateTotalSale();


}