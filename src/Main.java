
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static boolean isAlphabetic(String str) {
        return str.matches("[a-zA-Z]+");
    }

    public static void writeToFile(String data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println(">>> LỖI: Không thể ghi dữ liệu vào file!");
        }
    }

    public static void writeProductToFile(Product product, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(product.getInfo());
            writer.newLine();  // Adds a newline after the product's details
        } catch (IOException e) {
            System.out.println(">>> LỖI: Không thể ghi sản phẩm vào file!");
        }
    }

    public static void readProductsFromFile(Store store, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Remove any unwanted labels or extra spaces before parsing
                line = line.trim();
                String[] productDetails = line.split(",");  // Assuming data is comma-separated

                if (productDetails.length == 4) {
                    try {
                        // Parsing each part of the product details
                        int productId = Integer.parseInt(productDetails[0].replaceAll("[^0-9]", ""));  // Extracts digits only
                        String name = productDetails[1].replace("Name: ", "").trim();  // Removes label and trims
                        double price = Double.parseDouble(productDetails[2].replace("Price: ", "").trim());
                        int quantity = Integer.parseInt(productDetails[3].replace("Quantity: ", "").trim());

                        // Create and add the product to the store
                        Product product = new Product(productId, name, price, quantity);
                        store.addProduct(product);
                    } catch (NumberFormatException e) {
                        System.out.println(">>> LỖI: Dữ liệu sản phẩm không hợp lệ: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(">>> LỖI: Không thể đọc sản phẩm từ file!");
        }
    }

    public static void readOrdersFromFile(Store store, String fileName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        Order order = null;
        Customer customer = null;
        List<Product> products = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            line = line.trim(); // Loại bỏ khoảng trắng thừa

            // Kiểm tra dòng bắt đầu bằng "Order ID:"
            if (line.startsWith("Order ID:")) {
                // Nếu có đơn hàng cũ, lưu vào store trước khi tạo đơn hàng mới
                if (order != null) {
                    // Thêm sản phẩm vào đơn hàng
                    for (Product product : products) {
                        order.addProduct(product, product.getQuantity());
                    }
                    store.addOrder(order); // Thêm đơn hàng vào cửa hàng
                }
                // Lấy Order ID từ dòng
                int orderId = Integer.parseInt(line.substring("Order ID:".length()).trim());
                order = new Order(orderId, null); // Tạo đơn hàng mới
                products.clear(); // Đặt lại danh sách sản phẩm cho đơn hàng mới
            } 
            // Kiểm tra dòng bắt đầu bằng "Customer:"
            else if (line.startsWith("Customer:")) {
                String[] customerDetails = line.substring("Customer:".length()).trim().split(",");
                int customerId = Integer.parseInt(customerDetails[0].split(":")[1].trim());
                String customerName = customerDetails[1].split(":")[1].trim();
                String customerEmail = customerDetails[2].split(":")[1].trim();
                customer = new Customer(customerId, customerName, customerEmail);

                if (order != null) {
                    order.setCustomer(customer); // Gán khách hàng vào đơn hàng
                }
            } 
            // Kiểm tra dòng bắt đầu bằng "Total Amount:"
            else if (line.startsWith("Total Amount:")) {
                double totalAmount = Double.parseDouble(line.substring("Total Amount:".length()).trim());
                if (order != null) {
                    order.setTotalAmount(totalAmount); // Gán tổng tiền cho đơn hàng
                }
            } 
            // Kiểm tra dòng bắt đầu bằng "Product ID:"
            else if (line.startsWith("Product ID:")) {
                String[] productDetails = line.split(",");
                int productId = Integer.parseInt(productDetails[0].split(":")[1].trim());
                String productName = productDetails[1].split(":")[1].trim();
                double productPrice = Double.parseDouble(productDetails[2].split(":")[1].trim());
                int productQuantity = Integer.parseInt(productDetails[3].split(":")[1].trim());

                // Tạo sản phẩm và thêm vào danh sách sản phẩm
                Product product = new Product(productId, productName, productPrice, productQuantity);
                products.add(product);
            }

            // Khi kết thúc một đơn hàng (dòng trống hoặc cuối file)
            if (line.isEmpty() && order != null) {
                for (Product product : products) {
                    order.addProduct(product, product.getQuantity()); // Thêm sản phẩm vào đơn hàng
                }
                store.addOrder(order); // Thêm đơn hàng vào cửa hàng
                order = null; // Reset cho đơn hàng mới
                products.clear(); // Xóa danh sách sản phẩm
            }
        }

        // Thêm đơn hàng cuối cùng nếu có
        if (order != null) {
            for (Product product : products) {
                order.addProduct(product, product.getQuantity()); // Thêm sản phẩm vào đơn hàng
            }
            store.addOrder(order);
        }

        System.out.println(">>> Đơn hàng đã được tải thành công từ " + fileName);
    } catch (IOException e) {
        System.out.println(">>> LỖI: Không thể đọc file " + fileName);
    } catch (NumberFormatException e) {
        System.out.println(">>> LỖI: Định dạng dữ liệu không hợp lệ trong file " + fileName);
    }
}


    public static void updateProductFile(List<Product> products, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Product product : products) {
                // Ghi lại thông tin từng sản phẩm vào file
                writer.write(product.getInfo()); // Giả sử method getInfo() trả về thông tin sản phẩm dưới dạng chuỗi
                writer.newLine(); // Thêm dòng mới sau mỗi sản phẩm
            }
            System.out.println(">>> Cập nhật thông tin sản phẩm vào file thành công!");
        } catch (IOException e) {
            System.out.println(">>> LỖI: Không thể ghi thông tin sản phẩm vào file!");
        }
    }

    public static void main(String[] args) {
        Store store = new Store();
        Scanner scanner = new Scanner(System.in);
        String fileName = "orders.txt";
        List<Product> productsStore = store.getProducts();
        while (true) {
            clearScreen();

            System.out.println("\n==============================");
            System.out.println("         QUẢN LÝ CỬA HÀNG");
            System.out.println("==============================");
            System.out.println("1. Thêm mới khách hàng");
            System.out.println("2. Tạo order khách hàng");
            System.out.println("3. In danh sách đơn hàng của cửa hàng");
            System.out.println("4. Thêm sản phẩm vào cửa hàng");
            System.out.println("5. In danh sách sản phẩm của cửa hàng");
            System.out.println("6. Thoát");
            System.out.println("==============================");
            System.out.print("Chọn một tùy chọn (1-6): ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("\n>>> LỖI: Vui lòng nhập một số nguyên hợp lệ (1-6)!");
                scanner.nextLine();
                System.out.println("Nhấn Enter để tiếp tục...");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    clearScreen();

                    System.out.println("--- TẠO MỚI ĐƠN HÀNG ---");

                    int customerId;
                    while (true) {
                        try {
                            System.out.print("Nhập ID khách hàng (chỉ số nguyên): ");
                            customerId = scanner.nextInt();
                            scanner.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("\n>>> LỖI: Vui lòng nhập một số nguyên!");
                            scanner.nextLine();
                        }
                    }

                    String name;
                    while (true) {
                        System.out.print("Nhập tên khách hàng (chỉ chữ cái): ");
                        name = scanner.nextLine();
                        if (isAlphabetic(name)) {
                            break;
                        } else {
                            System.out.println("\n>>> LỖI: Tên chỉ được chứa các chữ cái!");
                        }
                    }

                    System.out.print("Nhập email khách hàng: ");
                    String email = scanner.nextLine();

                    Customer customer = new Customer(customerId, name, email);
                    Order order = new Order(store.getAllOrders().size() + 1, customer);
                    store.addOrder(order);

                    break;

                case 2:
                    System.out.println("Nhập email khách hàng muốn mua đồ:");
                    String searchEmail = scanner.nextLine();
                    String choiceOrder = "yes";
                    for (int i = 0; i < store.getAllOrders().size(); i++) {
                        if (searchEmail.equalsIgnoreCase(store.getAllOrders().get(i).getCustomer().getEmail())) {
                            System.out.println("Khách hàng " + store.getAllOrders().get(i).getCustomer().getName());
                            System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
                            List<Product> products = store.getProducts();
                            for (int j = 0; j < products.size(); j++) {
                                System.out.println((j + 1) + ". " + products.get(j).getInfo());
                            }

                            do {
                                int productChoice;
                                int quantity;
                                try {
                                    System.out.print("Chọn sản phẩm (nhập số): ");
                                    productChoice = scanner.nextInt();
                                    System.out.print("Nhập số lượng: ");
                                    quantity = scanner.nextInt();

                                    if (productChoice > 0 && productChoice <= products.size()) {
                                        Product selectedProduct = products.get(productChoice - 1);
                                        if (quantity <= selectedProduct.getQuantity()) {
                                            // Thêm sản phẩm vào đơn hàng
                                            store.getAllOrders().get(i).addProduct(selectedProduct, quantity);

                                            // Sau khi thêm sản phẩm vào đơn hàng, giảm số lượng trong kho
                                            selectedProduct.setQuantity(selectedProduct.getQuantity());

                                            System.out.println("\n>>> Đơn hàng đã được tạo thành công!");

                                            // Cập nhật lại danh sách sản phẩm trong file sau khi mua
                                            updateProductFile(store.getProducts(), "products.txt"); // Cập nhật lại sản phẩm vào file

                                            // Ghi đơn hàng vào file orders.txt
                                            writeToFile(store.getAllOrders().get(i).getOrderDetails(), "orders.txt");

                                        } else {
                                            System.out.println("\n>>> LỖI: Số lượng không đủ!");
                                           
                                        }
                                    } else {
                                        System.out.println("\n>>> LỖI: Lựa chọn không hợp lệ!");
                                    }

                                } catch (InputMismatchException e) {
                                    System.out.println("\n>>> LỖI: Nhập sai định dạng! Vui lòng thử lại.");
                                    scanner.nextLine();
                                }
                                scanner.nextLine();
                                System.out.println("Bạn có muốn mua thêm hàng hoặc mặt hàng khác không? (yes/no)");
                                choiceOrder = scanner.nextLine();
                            } while (choiceOrder.equalsIgnoreCase("yes"));

                            System.out.print("Tổng tiền cần thanh toán là: " + store.getAllOrders().get(i).calculateTotal());

                            System.out.println("Nhấn Enter để tiếp tục...");
                            scanner.nextLine();
                        }

                    }
                    break;

                case 3:
                    clearScreen();
                    System.out.println("--- DANH SÁCH ĐƠN HÀNG ---");

                    // Xóa các đơn hàng cũ để tránh lặp dữ liệu
                    store.clearOrders();
                    readOrdersFromFile(store, fileName);

                    // Hiển thị danh sách đơn hàng
                    List<Order> orders = store.getAllOrders();
                    if (orders.isEmpty()) {
                        System.out.println(">>> Không có đơn hàng nào.");
                    } else {
                        for (Order orderDetails : orders) {
                            System.out.println(orderDetails.getOrderDetails());
                        }
                    }

                    System.out.println("Nhấn Enter để tiếp tục...");
                    scanner.nextLine();
                    break;

                case 4:
                    System.out.print("Enter number of products to add to store: ");
                    int numProducts = scanner.nextInt();
                    scanner.nextLine();

                    for (int i = 0; i < numProducts; i++) {
                        try {
                            System.out.print("Enter Product ID: ");
                            int productId = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Enter Product Name: ");
                            String productName = scanner.nextLine();

                            System.out.print("Enter Product Price: ");
                            double productPrice = scanner.nextDouble();
                            scanner.nextLine();

                            System.out.print("Enter Product Quantity: ");
                            int productQuantity = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Is this a digital product? (yes/no): ");
                            String isDigital = scanner.nextLine();

                            if (isDigital.equalsIgnoreCase("yes")) {
                                System.out.print("Enter File Size (in MB): ");
                                double fileSize = scanner.nextDouble();
                                scanner.nextLine();
                                store.addProduct(new DigitalProduct(productId, productName, productPrice, productQuantity, fileSize));
                            } else {
                                store.addProduct(new Product(productId, productName, productPrice, productQuantity));
                            }
                            writeProductToFile(new Product(productId, productName, productPrice, productQuantity), "products.txt");
                            System.out.println("Product added successfully!");
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter the correct data type.");
                            scanner.nextLine();
                            i--;
                        }
                    }
                    break;
                case 5:
                    clearScreen();
                    System.out.println("--- DANH SÁCH SẢN PHẨM ---");

                    // Xóa sản phẩm cũ trước khi đọc từ file để tránh lặp
                    store.clearProducts();
                    readProductsFromFile(store, "products.txt");  // Đọc sản phẩm từ file "products.txt"

                    // Lấy danh sách sản phẩm từ cửa hàng
                    if (productsStore.isEmpty()) {
                        System.out.println(">>> Chưa có sản phẩm");
                    } else {
                        // In danh sách các sản phẩm trong cửa hàng
                        for (Product productDetails : productsStore) {
                            System.out.println(productDetails.getInfo());
                        }
                    }

                    System.out.println("Nhấn Enter để tiếp tục...");
                    scanner.nextLine();
                    break;

                case 6:
                    clearScreen();
                    System.out.println(">>> Thoát chương trình. Tạm biệt!");
                    scanner.close();
                    return;

                default:
                    System.out.println("\n>>> LỖI: Lựa chọn không hợp lệ! Vui lòng chọn 1-6.");
                    System.out.println("Nhấn Enter để tiếp tục...");
                    scanner.nextLine();
            }
        }
    }
}
