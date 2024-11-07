import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Order createOrder(Customer customer) {
        return new Order(customer);
    }

    // Phương thức ghi danh sách sản phẩm ra file
    public void saveProductsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) { // Chế độ append = true
            for (Product product : products) {
                writer.write(product.getInfo() + "\n");
            }
            System.out.println("Products saved to file successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving products to file: " + e.getMessage());
        }
    }
}
