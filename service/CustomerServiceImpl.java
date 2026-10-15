package service;

import model.Customer;
import model.OrderItem;
import model.Product;

public interface CustomerServiceImpl {
    public Customer[] ShowAllCustomers();
    public Customer  FindCustomerById(int id);
    public void addCustomer(Customer cr);
    public void removeCustomer(Customer cr);
    public void editCustomer(Customer cr);
}
