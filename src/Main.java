import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store();

        // Add products to store
        addProductsToStore(store, scanner);

        // Create customer
        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        String customerEmail = scanner.nextLine();
        Customer customer = new Customer(customerId, customerName, customerEmail);

        // Create order for customer
        Order order = store.createOrder(customer);

        // Add products to order
        addProductsToOrder(order, store, scanner);

        // Print order details
        System.out.println("Order created successfully!");
        System.out.println(order.getOrderDetails());

        scanner.close();
    }

    private static void addProductsToStore(Store store, Scanner scanner) {
        System.out.print("Enter number of products to add to store: ");
        int numProducts = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 0; i < numProducts; i++) {
            try {
                System.out.print("Enter Product ID: ");
                int productId = scanner.nextInt();
                scanner.nextLine(); // consume newline

                System.out.print("Enter Product Name: ");
                String productName = scanner.nextLine();

                System.out.print("Enter Product Price: ");
                double productPrice = scanner.nextDouble();
                scanner.nextLine(); // consume newline

                System.out.print("Enter Product Quantity: ");
                int productQuantity = scanner.nextInt();
                scanner.nextLine(); // consume newline

                System.out.print("Is this a digital product? (yes/no): ");
                String isDigital = scanner.nextLine();

                if (isDigital.equalsIgnoreCase("yes")) {
                    System.out.print("Enter File Size (in MB): ");
                    double fileSize = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    store.addProduct(new DigitalProduct(productId, productName, productPrice, productQuantity, fileSize));
                } else {
                    store.addProduct(new Product(productId, productName, productPrice, productQuantity));
                }
                System.out.println("Product added successfully!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter the correct data type.");
                scanner.nextLine(); // consume invalid input
                i--; // repeat this iteration
            }
        }
    }

    private static void addProductsToOrder(Order order, Store store, Scanner scanner) {
        int productChoice;
        do {
            System.out.println("Available Products:");
            for (Product product : store.getProducts()) {
                System.out.println(product.getInfo());
            }

            System.out.print("Enter Product ID to add to order (0 to finish): ");
            productChoice = scanner.nextInt();

            if (productChoice != 0) {
                Product product = null;
                for (Product p : store.getProducts()) {
                    if (p.getProductId() == productChoice) {
                        product = p;
                        break;
                    }
                }

                if (product != null) {
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    order.addProduct(product, quantity);
                    System.out.println("Product added to order.");
                } else {
                    System.out.println("Product not found.");
                }
            }
        } while (productChoice != 0);
    }
}
