package model;

public class Order {
    private int id;
    private int CustomerId;
    private double TotalPrice;
    private String Status;
    public Order(int id, int CustomerId,double TotalPrice, String Status) {
        this.id = id;
        this.CustomerId = CustomerId;
        this.TotalPrice=TotalPrice;
        this.Status = Status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }


    public String getStatus() {
        return Status;
    }
    public double getTotalPrice(){
        return TotalPrice;
    }
    public void setTotalPrice(double ttlp){
        TotalPrice=ttlp;
    }

    public void setStatus(String status) {
        Status = status;
    }
    void ChangeStatus(String newstatus){
        Status=newstatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", CustomerId=" + CustomerId +
                ", Status='" + Status + '\'' +
                '}';
    }

}
