import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OnlineStoreApp {
    static ArrayList<Product> products = new ArrayList<>();
    static ArrayList<Product> cart = new ArrayList<>();

    public static void main(String[] args) {
        loadInventory("inventory.csv");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Store Home Screen");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    showProducts();
                    break;
                case 2:
                    showCart();
                    break;
                case 3:
                    System.exit(0);
            }
        }
    }

    private static void showProducts() {
        System.out.println("Show Products");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i).getId() + " | " + products.get(i).getName() + " | " + products.get(i).getPrice());
        }
        System.out.print("Enter product id to add to cart or X to go back to home screen: ");
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine();
        if (!id.equalsIgnoreCase("X")) {
            for (Product product : products) {
                if (product.getId().equals(id)) {
                    cart.add(product);
                }
            }
        }
    }

    private static void showCart() {
        System.out.println("Show Cart");
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            System.out.println((i + 1) + ". " + cart.get(i).getId() + " | " + cart.get(i).getName() + " | " + cart.get(i).getPrice());
            total += cart.get(i).getPrice();
        }
        System.out.println("Total: " + total);
        System.out.println("C. Check Out");
        System.out.println("X. Go back to home screen");
        System.out.print("Enter your choice: ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("C")) {
            checkOut(total);
        }
    }

    private static void checkOut(double total) {
        System.out.println("Check Out");
        System.out.println("Total amount owed: " + total);
        System.out.print("Enter payment amount: ");
        Scanner sc = new Scanner(System.in);
        double payment = sc.nextDouble();
        if (payment < total) {
            System.out.println("Not enough payment. Please enter the full amount.");
            checkOut(total);
        } else {
            double change = payment - total;
            System.out.println("Change: " + change);
            System.out.println("Items sold: ");
            for (Product product : cart) {
                System.out.println(product.getId() + " | " + product.getName() + " | " + product.getPrice());
            }
            cart.clear();
            System.out.println("Transaction complete.");
        }
    }

    private static void loadInventory(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] productDetails = line.split("\\|");
                products.add(new Product(productDetails[0], productDetails[1], Double.parseDouble(productDetails[2])));
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory file.");
        }
    }

    static class Product {
        private String id;
        private String name;
        private double price;

        public Product(String id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}


