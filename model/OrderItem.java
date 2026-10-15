package model;

public class OrderItem {
    private int OrderId;
    private int ProductId;
    private int Quantity;
    private double UnitPrice;

    public OrderItem(int OrderId, int ProductId, int Quantity, double UnitPrice) {
        this.OrderId = OrderId;
        this.ProductId = ProductId;
        this.Quantity = Quantity;
        this.UnitPrice = UnitPrice;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }
    public double SubTotal(){
        return Quantity*UnitPrice;
    }
}
