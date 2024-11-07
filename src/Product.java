public class Product {
    protected int productId;
    protected String name;
    protected double price;
    protected int quantity;
    private int orderQuantity;

    public Product(int productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public int orderQuantity(int quantity){
        this.orderQuantity = quantity;
        return orderQuantity;
    }
    public void updateQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public String getInfo() {
        return "Product ID: " + productId + ", Name: " + name + ", Price: " + price + ", Quantity: " + quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}