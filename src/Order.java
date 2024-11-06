import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author OS
 */
public class Order{

    int orderId;
    Customer customer;
    List<Product> products;
    double totalAmount;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.products = new ArrayList<>();
        this.totalAmount = 0.0;
    }
    
    public void addProduct(Product product, int quantity){
        product.updateQuantity(quantity);
        products.add(product);
    }
    
    public double calculateTotal(){
        totalAmount = 0.0;
        for(Product prd : products){
            totalAmount += prd.getPrice() *  prd.getQuantity();
        }
        return totalAmount;
    }
    
    public String getOrderDetails(){
        StringBuilder details = new StringBuilder();
        details.append("Order ID: ").append(orderId);
        details.append("Customer Info: ").append(customer.getInfo()).append("\n");
        details.append("Product List: ");
        for(Product prd: products){
            details.append(prd.getInfo()).append("\n");
        }
        details.append(totalAmount).append("\n");
        return details.toString();
    }
}