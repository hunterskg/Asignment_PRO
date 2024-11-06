import java.util.ArrayList;
import java.util.List;

public class Order {
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();
    private double totalPrice = 0.0;

    public Order(Customer customer) {
        this.customer = customer;
    }

    public void addProduct(Product product, int quantity) {
        if (product.getQuantity() >= quantity) {
            product = new Product(product.getProductId(), product.getName(), product.getPrice(), quantity);
            items.add(new OrderItem(product, quantity));
            totalPrice += product.getPrice() * quantity;
            System.out.println("Product added to order.");
        } else {
            System.out.println("Insufficient stock for " + product.getName());
        }
    }

    public String getOrderDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Order for " + customer.getName() + " (ID: " + customer.getCustomerId() + ")\n");
        for (OrderItem item : items) {
            details.append(item.getProduct().getInfo() + ", Quantity: " + item.getQuantity() + "\n");
        }
        details.append("Total: $" + totalPrice);
        return details.toString();
    }
}
