package service;
import exception.DuplicateIdException;
import exception.InsufficientStockException;
import exception.ProductNotFoundException;
import model.OrderItem;
import model.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.IOException;

import model.Product;

public class ProductService implements ProductServiceImpl{
    @Override
    public Product[] ShowAllProducts() {
        try (
                Reader reader = new FileReader("data/products.csv");
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .get()
                        .parse(reader)
        ) {
            CSVRecord[] records = parser.getRecords().toArray(new CSVRecord[0]);
            if (records.length==0){
                throw new ProductNotFoundException("هنوز کالایی ثبت نشده است.");
            }
            Product[] prdArray=new Product[records.length];
            int index=0;
            for(int i=0;i<records.length;i++){
                CSVRecord record = records[i];
                int id=Integer.parseInt(record.get("id"));
                String name=record.get("name");
                String category=record.get("category");
                double price=Double.parseDouble(record.get("price"));
                int quantity=Integer.parseInt(record.get("quantity"));
                Product Prc=new Product(id,name,category,price,quantity);
                prdArray[index]=Prc;
                index++;

            }
            return prdArray;


        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Product  FindProductById(int id) {
        try (
                Reader reader = new FileReader("data/products.csv");
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .get()
                        .parse(reader)
        ) {
            CSVRecord[] records = parser.getRecords().toArray(new CSVRecord[0]);
            Product Pr=null;
            for(int i=0;i<records.length;i++){
                CSVRecord record = records[i];
                int ID=Integer.parseInt(record.get("id"));
                if(ID==id){
                    String name=record.get("name");
                    String category=record.get("category");
                    double price=Double.parseDouble(record.get("price"));
                    int quantity=Integer.parseInt(record.get("quantity"));
                    Pr=new Product(id,name,category,price,quantity);
                    break;
                }

            }
            if(Pr==null){
                throw new ProductNotFoundException("هیچ کالایی با این ایدی پیدا نشد");
            }
            return Pr;

        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addProduct(Product pr) {
        try(FileWriter writer=new FileWriter("data/products.csv",true);
            CSVPrinter printer=new CSVPrinter(writer,CSVFormat.DEFAULT)
        ){
            int id=pr.getId();
            String name=pr.getName();
            String category=pr.getCategory();
            double price=pr.getPrice();
            int quantity=pr.getQuantity();
            try {
                FindProductById(id);

                throw new DuplicateIdException("این ایدی قبلا در فایل ذخیره شده است");
            } catch (ProductNotFoundException e) {
                System.out.println("محصولی با این آیدی وجود ندارد");
            }
            printer.printRecord(id,name,category,price,quantity);
        }catch(IOException e){
            throw new RuntimeException("خطا در ذخیره فایل", e);

        }

    }

    @Override
    public void removeProduct(Product pr) {
        CSVRecord[] records;
        CSVRecord[] records2;
        int index=0;
        FindProductById(pr.getId());

        try (
                Reader reader = new FileReader("data/products.csv");
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .get()
                        .parse(reader);

        ){
            records = parser.getRecords().toArray(new CSVRecord[0]);
            records2 =new CSVRecord[records.length] ;
            for(int i=0;i<records.length;i++){
                CSVRecord record = records[i];
                int id=Integer.parseInt(record.get("id"));
                if(id!=pr.getId()){
                    records2[index]=record;
                    index++;

                }
            }

        }catch(Exception e){
            throw new RuntimeException("خطا در خواندن",e);

        }
        try(FileWriter writer=new FileWriter("data/products.csv");
            CSVPrinter printer=new CSVPrinter(writer,CSVFormat.DEFAULT)){
            printer.printRecord("id","name","category","price","quantity");
            for(int j=0;j<index;j++){
                CSVRecord record2=records2[j];
                printer.printRecord(
                        record2.get("id"),record2.get("name"),record2.get("category"),
                        record2.get("price"),record2.get("quantity")
                );
            }
        }catch(Exception e){
            throw new RuntimeException("خطا در ذخیره فایل", e);
        }

    }

    @Override

    public void editProduct(Product pr) {
        FindProductById(pr.getId());

        CSVRecord[] records;

        try (
                Reader reader = new FileReader("data/products.csv");
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .get()
                        .parse(reader)
        ) {
            records = parser.getRecords().toArray(new CSVRecord[0]);

        } catch (IOException e) {
            throw new RuntimeException("خطا در خواندن فایل", e);
        }

        try (
                FileWriter writer = new FileWriter("data/products.csv");
                CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)
        ) {
            printer.printRecord("id", "name", "category", "price", "quantity");

            for (int i = 0; i < records.length; i++) {
                CSVRecord record = records[i];

                int id = Integer.parseInt(record.get("id"));
                String name = record.get("name");
                String category = record.get("category");
                double price = Double.parseDouble(record.get("price"));
                int quantity = Integer.parseInt(record.get("quantity"));

                if (id == pr.getId()) {
                    name = pr.getName();
                    category = pr.getCategory();
                    price = pr.getPrice();
                    quantity = pr.getQuantity();
                }

                printer.printRecord(id, name, category, price, quantity);
            }

        } catch (IOException e) {
            throw new RuntimeException("خطا در ذخیره فایل", e);
        }
    }

    public void QuantityReducer(Product pr, int quantity) {
        FindProductById(pr.getId());

        if (quantity <= 0) {
            throw new IllegalArgumentException("تعداد باید بیشتر از صفر باشد.");
        }

        if (pr.getQuantity() < quantity) {
            throw new InsufficientStockException("موجودی کالا کافی نیست.");
        }

        int newQuantity = pr.getQuantity() - quantity;

        Product newProduct = new Product(
                pr.getId(),
                pr.getName(),
                pr.getCategory(),
                pr.getPrice(),
                newQuantity
        );

        editProduct(newProduct);
    }
    public Product[] ShowLowStockProducts(){
        Product[] products=ShowAllProducts();
        Product[] LowStockProducts=new Product[products.length];
        int index=0;
        for(int i=0;i<products.length;i++){
            Product prc=products[i];
            if(prc.getQuantity()<5){
                LowStockProducts[index]=prc;
                index++;

            }
        }
        return LowStockProducts;

    }
    public Product[] ShowOutOfStockProducts(){
        Product[] products=ShowAllProducts();
        Product[] OutOfStockProducts=new Product[products.length];
        int index=0;
        for(int i=0;i<products.length;i++){
            Product prc=products[i];
            if(prc.getQuantity()==0){
                OutOfStockProducts[index]=prc;
                index++;

            }
        }
        return OutOfStockProducts;

    }

}
