
import java.util.ArrayList;
import java.util.List;

public class Store {

    private List<Product> products;
    private List<Order> orders;

    public Store() {
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order createOrder(Customer customer) {
        Order order = new Order(orders.size() + 1, customer);
        orders.add(order);
        return order;
    }

    public Product findProductById(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;  // Returns null if no matching product is found
    }

    public void clearOrders() {
        orders.clear();
    }

    public void clearProducts() {
        products.clear();
    }
}
