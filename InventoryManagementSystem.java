import java.util.*;

class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;

    public Product(int id, String name, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + category + " | $" + price + " | Quantity: " + quantity;
    }
}

class Supplier {
    private int id;
    private String name;
    private String contactInfo;

    public Supplier(int id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | Contact: " + contactInfo;
    }
}

class Order {
    private int orderId;
    private List<Product> products;
    private double totalAmount;

    public Order(int orderId, List<Product> products) {
        this.orderId = orderId;
        this.products = products;
        this.totalAmount = calculateTotal();
    }

    private double calculateTotal() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        StringBuilder orderDetails = new StringBuilder("Order ID: " + orderId + "\n");
        for (Product product : products) {
            orderDetails.append(product.toString()).append("\n");
        }
        orderDetails.append("Total: $" + totalAmount);
        return orderDetails.toString();
    }
}

class InventoryManager {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void updateProductQuantity(int productId, int newQuantity) {
        for (Product product : products) {
            if (product.getId() == productId) {
                product.setQuantity(newQuantity);
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public void displayProducts() {
        for (Product product : products) {
            System.out.println(product);
        }
    }
}

class SupplierManager {
    private List<Supplier> suppliers = new ArrayList<>();

    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }

    public void displaySuppliers() {
        for (Supplier supplier : suppliers) {
            System.out.println(supplier);
        }
    }
}

class OrderManager {
    private List<Order> orders = new ArrayList<>();
    private InventoryManager inventoryManager;

    public OrderManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public void placeOrder(List<Integer> productIds, List<Integer> quantities) {
        List<Product> orderedProducts = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            Product product = inventoryManager.getProductById(productIds.get(i));
            if (product != null && product.getQuantity() >= quantities.get(i)) {
                orderedProducts.add(new Product(product.getId(), product.getName(), product.getCategory(), product.getPrice(), quantities.get(i)));
                inventoryManager.updateProductQuantity(product.getId(), product.getQuantity() - quantities.get(i));
            } else {
                System.out.println("Product not available or insufficient stock: " + product.getName());
                return;
            }
        }
        Order order = new Order(orders.size() + 1, orderedProducts);
        orders.add(order);
        System.out.println("Order placed successfully!");
        System.out.println(order);
    }

    public void displayOrders() {
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}

public class InventoryManagementSystem {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        SupplierManager supplierManager = new SupplierManager();
        OrderManager orderManager = new OrderManager(inventoryManager);

        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n=== Inventory Management System ===");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product Quantity");
            System.out.println("3. Display Products");
            System.out.println("4. Add Supplier");
            System.out.println("5. Display Suppliers");
            System.out.println("6. Place Order");
            System.out.println("7. Display Orders");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Product Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    inventoryManager.addProduct(new Product(id, name, category, price, quantity));
                    break;

                case 2:
                    System.out.print("Enter Product ID to update quantity: ");
                    int productId = scanner.nextInt();
                    System.out.print("Enter new quantity: ");
                    int newQuantity = scanner.nextInt();
                    inventoryManager.updateProductQuantity(productId, newQuantity);
                    break;

                case 3:
                    inventoryManager.displayProducts();
                    break;

                case 4:
                    System.out.print("Enter Supplier ID: ");
                    int supplierId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Supplier Name: ");
                    String supplierName = scanner.nextLine();
                    System.out.print("Enter Contact Info: ");
                    String contactInfo = scanner.nextLine();
                    supplierManager.addSupplier(new Supplier(supplierId, supplierName, contactInfo));
                    break;

                case 5:
                    supplierManager.displaySuppliers();
                    break;

                case 6:
                    List<Integer> productIds = new ArrayList<>();
                    List<Integer> quantities = new ArrayList<>();
                    System.out.print("Enter number of products to order: ");
                    int numProducts = scanner.nextInt();
                    for (int i = 0; i < numProducts; i++) {
                        System.out.print("Enter Product ID: ");
                        productIds.add(scanner.nextInt());
                        System.out.print("Enter Quantity: ");
                        quantities.add(scanner.nextInt());
                    }
                    orderManager.placeOrder(productIds, quantities);
                    break;

                case 7:
                    orderManager.displayOrders();
                    break;

                case 8:
                    System.out.println("Exiting system...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
