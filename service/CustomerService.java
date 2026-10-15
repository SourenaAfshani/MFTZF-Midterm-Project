package service;

import exception.DuplicateIdException;
import exception.ProductNotFoundException;
import model.Customer;
import model.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;

public class CustomerService implements CustomerServiceImpl{
    @Override
    public Customer[] ShowAllCustomers() {
        try (
                Reader reader = new FileReader("data/customers.csv");
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .get()
                        .parse(reader)
        ) {
            CSVRecord[] records = parser.getRecords().toArray(new CSVRecord[0]);
            Customer[] cusArray=new Customer[records.length];
            int index=0;
            for(int i=0;i<records.length;i++){
                CSVRecord record = records[i];
                int id=Integer.parseInt(record.get("id"));
                String name=record.get("name");
                String phone=record.get("phone");
                Customer cr=new Customer(id,name,phone);
                cusArray[index]=cr;
                index++;

            }
            return cusArray;

        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Customer FindCustomerById(int id) {
        try (
                Reader reader = new FileReader("data/customers.csv");
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .get()
                        .parse(reader)
        ) {
            CSVRecord[] records = parser.getRecords().toArray(new CSVRecord[0]);
            Customer cr=null;
            for(int i=0;i<records.length;i++){
                CSVRecord record = records[i];
                int ID=Integer.parseInt(record.get("id"));
                if(ID==id){
                    String name=record.get("name");
                    String phone=record.get("phone");
                    cr=new Customer(id,name,phone);
                    break;
                }

            }
            if(cr==null){
                throw new ProductNotFoundException("هیچ مشتری ای با این ایدی پیدا نشد");
            }
            return cr;

        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addCustomer(Customer cr) {
        try(Writer writer=new FileWriter("data/customers.csv",true);
            CSVPrinter printer=new CSVPrinter(writer,CSVFormat.DEFAULT)){
            int id=cr.getId();
            String name= cr.getName();
            String phone=cr.getPhoneNumber();
            try {
                FindCustomerById(id);
                throw new DuplicateIdException("این ایدی قبلا در فایل ذخیره شده است");

            } catch (ProductNotFoundException e) {
            }
            printer.printRecord(id,name,phone);

        }
        catch(IOException e){
            System.out.println("خطا در خواندن و نوشتن در فایل");

        }

    }

    @Override
    public void removeCustomer(Customer cr) {

        CSVRecord[] records;
        CSVRecord[] records2;
        int index=0;
        FindCustomerById(cr.getId());

        try (
                Reader reader = new FileReader("src/data/customers.csv");
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
                if(id!=cr.getId()){
                    records2[index]=record;
                    index++;

                }
            }

        }catch(Exception e){
            throw new RuntimeException("خطا در خواندن",e);

        }
        try(FileWriter writer=new FileWriter("src/data/customers.csv");
            CSVPrinter printer=new CSVPrinter(writer,CSVFormat.DEFAULT)){
            printer.printRecord("id","name","phone");
            for(int j=0;j<index;j++){
                CSVRecord record2=records2[j];
                printer.printRecord(
                        record2.get("id"),record2.get("name"),record2.get("phone"));
            }
        }catch(Exception e){
            System.out.println(e);
        }

    }

    @Override

    public void editCustomer( Customer cr) {
        FindCustomerById(cr.getId());

        CSVRecord[] records;

        try (
                Reader reader = new FileReader("src/data/customers.csv");
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
                FileWriter writer = new FileWriter("src/data/customers.csv");
                CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)
        ) {
            printer.printRecord("id", "name", "phone");

            for (int i = 0; i < records.length; i++) {
                CSVRecord record = records[i];

                int forid = Integer.parseInt(record.get("id"));
                String name = record.get("name");
                String phone = record.get("phone");

                if (forid == cr.getId()) {
                    name = cr.getName();
                    phone = cr.getPhoneNumber();
                }

                printer.printRecord(forid, name, phone);
            }

        } catch (IOException e) {
            throw new RuntimeException("خطا در ذخیره فایل", e);
        }
    }

}
