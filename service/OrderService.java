package service;

import exception.CustomerNotFoundException;
import exception.InsufficientStockException;
import exception.OrderNotFoundException;
import exception.ProductNotFoundException;
import model.Order;
import model.OrderItem;
import model.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;

public class OrderService implements OrderServiceImpl{
    CustomerService cs;
    ProductService ps;
    public OrderService(CustomerService cs, ProductService ps) {
        this.cs = cs;
        this.ps = ps;
    }
    @Override
    public Order NewOrder(int OrderId, int CustomerId, OrderItem[] items) {
        cs.FindCustomerById(CustomerId);

        for (int i = 0; i < items.length; i++) {
            OrderItem item = items[i];

            Product product = ps.FindProductById(item.getProductId());

            if (!QuantityCheck(product,item)) {
                throw new InsufficientStockException("موجودی محصول کافی نیست");
            }
        }

        for (int i = 0; i < items.length; i++) {
            items[i].setOrderId(OrderId);

            Product product = ps.FindProductById(items[i].getProductId());
            items[i].setUnitPrice(product.getPrice());
        }
        double totalPrice = 0;

        for (int i = 0; i < items.length; i++) {
            Product product = ps.FindProductById(items[i].getProductId());
            totalPrice += product.getPrice() * items[i].getQuantity();
        }

        Order order = new Order(OrderId, CustomerId,totalPrice, "Pending");
        SaveOrderItems(items);
        SaveOrder(order);
        for(int i=0;i<items.length;i++){
            Product product=ps.FindProductById(items[i].getProductId());
            ps.QuantityReducer(product,items[i].getQuantity());
        }

        return order;
    }

    @Override
    public boolean QuantityCheck(Product pr,OrderItem Or) {
        Product product=ps.FindProductById(pr.getId());
        if(product==null){
            throw new ProductNotFoundException("محصولی با این آیدی پیدا نشد");
        }
        if(product.getQuantity()>=Or.getQuantity()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public double OrderTotal(Order or) {
        try(Reader reader=new FileReader("src/data/orders_items.csv");
            CSVParser parser= CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(reader)
        ){
            CSVRecord[] records = parser.getRecords().toArray(new CSVRecord[0]);
            double total=0;
            for(int i=0;i<records.length;i++) {
                CSVRecord record = records[i];
                int ID = Integer.parseInt(record.get("orderId"));
                if(ID==or.getId()) {
                    double price = Double.parseDouble(record.get("unitprice"));
                    int quantity = Integer.parseInt(record.get("quantity"));
                    total += price * quantity;
                }
            }
            return total;


        }catch(IOException e){
            throw new RuntimeException("خطا در خواندن",e);
        }
    }
    @Override
    public void SaveOrder(Order or) {
        try(Writer writer=new FileWriter("src/data/orders.csv",true);
        CSVPrinter printer=new CSVPrinter(writer,CSVFormat.DEFAULT)){
            int Id=or.getId();
            int CID=or.getCustomerId();
            double TotalPrice = or.getTotalPrice();
            String Status=or.getStatus();
            printer.printRecord(Id,CID,TotalPrice,Status);
        }
        catch(IOException e){
            throw new RuntimeException("خطا در ثبت سفارش",e);
        }

    }

    @Override
    public Order OrderSearch(int OrderId) {
        try(Reader reader=new FileReader("src/data/orders.csv");
        CSVParser parser=CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get()
                .parse(reader)){
            CSVRecord[] records = parser.getRecords().toArray(new CSVRecord[0]);
            for(int i=0;i<records.length;i++){
                CSVRecord record=records[i];
                int id=Integer.parseInt(record.get("id"));
                if(id==OrderId){
                    int CustomerId=Integer.parseInt(record.get("customerId"));
                    double totalPrice=Double.parseDouble(record.get("totalPrice"));
                    String Status=record.get("status");
                    return new Order(id, CustomerId, totalPrice,Status);

                }
            }
            throw new OrderNotFoundException("همچین سفارشی در این سامانه موجود نیست");

        }
        catch(IOException e){
            throw new RuntimeException("خطا در خواندن فایل سفارش‌ها", e);
        }
    }

    @Override
    public Order[] ShowCustomerOrder(int CustomerId) {
        try(Reader reader=new FileReader("src/data/orders.csv");
            CSVParser parser=CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(reader)){
            CSVRecord[] records = parser.getRecords().toArray(new CSVRecord[0]);
            int count=0;
            for (int i = 0; i < records.length; i++) {
                int customerId = Integer.parseInt(records[i].get("customerId"));

                if (customerId == CustomerId) {
                    count++;
                }
            }
            if(count==0){
                throw new CustomerNotFoundException("این مشتری در سامانه یافت نشد");
            }
            Order[] orders = new Order[count];
            int index = 0;
            for(int i=0;i<records.length;i++){
                CSVRecord record=records[i];
                int customerId=Integer.parseInt(record.get("customerId"));
                if(customerId==CustomerId){
                    int id=Integer.parseInt(record.get("id"));
                    double totalPrice=Double.parseDouble(record.get("totalPrice"));
                    String Status=record.get("status");
                    orders[index] = new Order(id, customerId, totalPrice, Status);
                    index++;

                }
            }
            return orders;

        }
        catch(IOException e){
            throw new RuntimeException("خطا در خواندن فایل سفارش‌ها", e);
        }

    }

    @Override
    public void CancelOrder(int OrderId) {
        CSVRecord[] records;
        String currentStatus = null;
        try (Reader reader = new FileReader("src/data/orders.csv");
             CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .get()
                        .parse(reader)
        ) {
            records = parser.getRecords().toArray(new CSVRecord[0]);
            for (int i = 0; i < records.length; i++) {
                CSVRecord record = records[i];
                int id = Integer.parseInt(record.get("id"));
                if (id == OrderId) {
                    currentStatus = record.get("status");
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("خطا در خواندن فایل سفارش‌ها", e);
        }

        if (currentStatus == null) {
            throw new OrderNotFoundException("همچین سفارشی با این آیدی موجود نیست");
        }
        try (
                Writer writer = new FileWriter("src/data/orders.csv");
                CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)
        ) {
            printer.printRecord("id", "customerId", "totalPrice", "status");

            for (int i = 0; i < records.length; i++) {
                CSVRecord record = records[i];
                int id = Integer.parseInt(record.get("id"));
                String status = record.get("status");
                if (id == OrderId) {
                    status = "Canceled";
                }
                printer.printRecord(record.get("id"), record.get("customerId")
                        , record.get("totalPrice"),
                        status);
            }

        } catch (IOException e) {
            throw new RuntimeException("خطا در ذخیره سفارش‌ها", e);
        }
        RestoreStock(OrderId);
    }
    @Override
    public void CompleteOrder(int OrderId) {
        try (Reader reader = new FileReader("src/data/orders.csv");
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .get().parse(reader)) {

            CSVRecord[] records = parser.getRecords().toArray(new CSVRecord[0]);

            boolean OrderFound = false;

            for (int i = 0; i < records.length; i++) {
                CSVRecord record = records[i];
                int id = Integer.parseInt(record.get("id"));

                if (id == OrderId) {
                    OrderFound = true;
                    String status = record.get("status");
                    if (status.equals("Canceled")) {
                        throw new IllegalArgumentException("سفارش کنسل‌شده را نمی‌توان تکمیل کرد");
                    }

                    if (status.equals("Completed")) {
                        throw new IllegalArgumentException("این سفارش قبلاً تکمیل شده است.");
                    }
                }
            }

            if (OrderFound == false) {
                throw new OrderNotFoundException("همچین سفارشی با این آیدی موجود نیست");
            }

            try (Writer writer = new FileWriter("src/data/orders.csv");
                 CSVPrinter printer = new CSVPrinter(
                         writer, CSVFormat.DEFAULT)) {

                printer.printRecord("id", "customerId", "totalPrice", "status");

                for (int i = 0; i < records.length; i++) {
                    CSVRecord record2 = records[i];
                    String status = record2.get("status");
                    int id = Integer.parseInt(record2.get("id"));
                    if (id == OrderId) {
                        status = "Completed";
                    }
                    printer.printRecord(
                            record2.get("id"),
                            record2.get("customerId"),
                            record2.get("totalPrice"),
                            status
                    );
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("خطا در خواندن فایل", e);
        }
    }
    public Order[] ShowAllOrders(){
        try (
                Reader reader = new FileReader("src/data/orders.csv");
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .get()
                        .parse(reader)
        ) {
            CSVRecord[] records = parser.getRecords().toArray(new CSVRecord[0]);
            if (records.length==0){
                throw new OrderNotFoundException("هنوز سفارشی ثبت نشده است.");
            }
            Order[] orders=new Order[records.length];
            int index=0;
            for(int i=0;i<records.length;i++){
                CSVRecord record = records[i];
                int id=Integer.parseInt(record.get("id"));
                int customerid=Integer.parseInt(record.get("customerId"));
                double totalprice=Double.parseDouble(record.get("totalPrice"));
                String status=record.get("status");
                Order or=new Order(id,customerid,totalprice,status);
                orders[index]=or;
                index++;

            }
            return orders ;


        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    public double CalculateTotalSale(){
        Order[] orders=ShowAllOrders();
        double TotalSale=0;
        for(int i=0;i< orders.length;i++){
            Order or=orders[i];
            if (or.getStatus().equals("Completed")) {
                TotalSale += OrderTotal(or);
            }
        }
        return TotalSale;

    }
    public void SaveOrderItems(OrderItem[] items) {
        try (
                Writer writer = new FileWriter("src/data/orders_items.csv", true);
                CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)
        ) {
            for (int i = 0; i < items.length; i++) {
                OrderItem item = items[i];
                printer.printRecord(
                        item.getOrderId(),
                        item.getProductId(), item.getQuantity(), item.getUnitPrice());
            }
        } catch (IOException e) {
            throw new RuntimeException("خطا در ذخیره جزئیات سفارش", e);
        }
    }
    public void RestoreStock(int OrderId) {

        try (Reader reader = new FileReader("src/data/orders_items.csv");
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .get()
                        .parse(reader)
        ) {
            CSVRecord[] records = parser.getRecords().toArray(new CSVRecord[0]);
            for (int i = 0; i < records.length; i++) {
                CSVRecord record = records[i];
                int orderId = Integer.parseInt(record.get("orderId"));
                if (orderId == OrderId) {
                    int productId = Integer.parseInt(record.get("productId"));
                    int quantity = Integer.parseInt(record.get("quantity"));
                    Product product = ps.FindProductById(productId);
                    Product updatedProduct = new Product(product.getId(), product.getName(),
                            product.getCategory(),
                            product.getPrice(),
                            product.getQuantity() + quantity);
                    ps.editProduct(updatedProduct);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("خطا در بازگرداندن موجودی محصولات", e);
        }
    }

}
