package model;

public class Customer {
    private int id;
    private String Name;
    private String PhoneNumber;

    public Customer(int id, String Name, String PhoneNumber) {
        this.id = id;
        this.Name = Name;
        this.PhoneNumber = PhoneNumber;
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

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
    void ChangeCsName(String newname){
        Name=newname;
    }
    void ChangeCsPhone(String newphone){
            Name=newphone;
   }
}


