import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private Customer customer;
    private List<Product> products;
    private double totalAmount;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.products = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    
    public void addProduct(Product product, int quantity) {
        product.orderQuantity(quantity);
        product.updateQuantity(quantity);
        products.add(product);
    }

    public double calculateTotal() {
        totalAmount = 0.0;
        for (Product product : products) {
            totalAmount += product.getPrice() * product.getOrderQuantity();
        }
        return totalAmount;
    }

    public String getOrderDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Order ID: ").append(orderId)
                .append("\nCustomer: ").append(customer.getInfo())
                .append("\nTotal Amount: ").append(calculateTotal())
                .append("\nProducts:\n");
        for (Product product : products) {
            details.append("Product ID: " + product.getProductId()+ ", Name: " + product.getName() + ", Price: " + product.getPrice()).append(", Quantity: "+product.getOrderQuantity());
        }
        return details.toString();
    } 
}