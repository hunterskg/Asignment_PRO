import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class Order {
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();
    private double totalPrice = 0.0;

    public Order(Customer customer) {
        this.customer = customer;
    }

    public boolean addProduct(Product product, int quantity) {
        if (product.getQuantity() >= quantity) {
            items.add(new OrderItem(product, quantity));
            product.setQuantity(product.getQuantity() - quantity);
            totalPrice += product.getPrice() * quantity;
            return true;
        } else {
            System.out.println("Insufficient stock for " + product.getName());
            return false;
        }
    }

    public String getOrderDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Order for " + customer.getName() + " (ID: " + customer.getCustomerId() + ")\n");
        for (OrderItem item : items) {
            details.append(item.getProduct().getInfo());
        }
        details.append("Total: $" + totalPrice);
        return details.toString();
    }

    // Phương thức ghi đơn hàng ra file
    public void saveOrderToFile(String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) { // Chế độ append = true
        writer.write("Order for Customer: " + customer.getName() + " (ID: " + customer.getCustomerId() + ")\n");
        for (OrderItem item : items) {
            writer.write(item.getProduct().getInfo() + ", Quantity: " + item.getQuantity() + "\n");
        }
        writer.write("Total Price: $" + totalPrice + "\n");
        writer.write("----------\n"); // Dòng phân cách giữa các đơn hàng
        System.out.println("Order saved to file successfully!");
    } catch (IOException e) {
        System.out.println("An error occurred while saving order to file: " + e.getMessage());
    }
}

}
