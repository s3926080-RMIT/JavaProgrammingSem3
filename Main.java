import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    //  Create ArrayList of products
    static ArrayList<Product> products_list = new ArrayList<>();

    static void listAllProducts() {
        System.out.printf("%-10s %-25s %-20s\n", "ID", "Name", "Price");
        for (Product p : products_list) {
            p.printBasicInfo();
        }
    }

    static void addProduct() {
//      Ask admin to input new product info
        Scanner scanner = new Scanner(System.in);

//      Input name
        System.out.print("Input new product name: ");
        String pName;
        while (true) {
            pName = scanner.nextLine();
//          Restrict input to avoid repeated product names
            boolean name_exists = false;
            for (Product p : products_list) {
                if (Objects.equals(pName, p.getpName())) {
                    name_exists = true;
                    break;
                }
            }
//          Also restrict input to not contain ',' as the character is used to divide product info
            if (pName.indexOf(',') == -1 && !name_exists) {
                break;
            }
            System.out.print("That is not a valid name. Please try again: ");
        }

//      Input price
        System.out.print("Input new product price: ");
        String pPrice_string;
//      Restrict input to only int
        while (true) {
            pPrice_string = scanner.nextLine();
            if (pPrice_string.matches("^[0-9]+$")) {
                break;
            }
            System.out.print("That is not a valid price. Please try again: ");
        }
        int pPrice = Integer.parseInt(pPrice_string);

//      Input category
        System.out.print("Input new product's category: ");
        String pCategory;
//      Restrict input to not contain ','
        while (true) {
            pCategory = scanner.nextLine();
            if (pCategory.indexOf(',') == -1) {
                break;
            }
            System.out.print("That is not a valid category. Please try again: ");
        }

//      Input description
        System.out.print("Input new product's descriptions: ");
        String pDescriptions;
//      Restrict input to not contain ','
        while (true) {
            pDescriptions = scanner.nextLine();
            if (pDescriptions.indexOf(',') == -1) {
                break;
            }
            System.out.print("That is not valid descriptions. Please try again: ");
        }

//      Generate unique product ID
        int temp_ID = products_list.size() + 1;
        String temp_ID_string;
        if (temp_ID < 10) {
            temp_ID_string = "P-00" + temp_ID;
        } else if (temp_ID < 100) {
            temp_ID_string = "P-0" + temp_ID;
        } else {
            temp_ID_string = "P-" + temp_ID;
        }

//      Add product to ArrayList
        Product p = new Product(temp_ID_string, pName, pPrice, pCategory, pDescriptions);
        products_list.add(p);

//      Append new product info to file
        try {
            FileWriter p_writer = new FileWriter("items.txt", true);
            p_writer.append(p.getpID()).append(",").append(p.getpName()).append(",").append(String.valueOf(p.getpPrice()
            )).append(",").append(p.getpCategory()).append(",").append(p.getpDescriptions()).append("\n");
            p_writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void changePrice() {
//      Ask admin to input ID of product to be repriced
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input product ID: ");
        String change_pID = scanner.nextLine();
        boolean product_found = false;

        for (Product p : products_list) {
            if (Objects.equals(p.getpID(), change_pID)) {
                System.out.print("Input new price: ");
//              Restrict input to only int
                String pPrice_string;
                while (true) {
                    pPrice_string = scanner.nextLine();
                    if (pPrice_string.matches("^[0-9]+$")) {
                        break;
                    }
                    System.out.print("That is not a valid price. Please try again: ");
                }
                p.setpPrice(Integer.parseInt(pPrice_string));

//              Rewrite product info to file
                try {
                    FileWriter p_writer = new FileWriter("items.txt");
                    for (Product p2 : products_list) {
                        p_writer.append(p2.getpID()).append(",").append(p2.getpName()).append(",").append(String.valueOf
                                (p2.getpPrice())).append(",").append(p2.getpCategory()).append(",").append(p.
                                getpDescriptions()).append("\n");
                    }
                    p_writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Product price changed.");
                product_found = true;
                break;
            }
        }
        if (!product_found) {
            System.out.println("No products with such ID found.");
        }
    }

    static void rmvProduct() {
//      Ask admin to input ID of product to be removed
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input product ID: ");
        String rmv_pID = scanner.nextLine();
        boolean product_found = false;

        for (Product p : products_list) {
            if (Objects.equals(p.getpID(), rmv_pID)) {
//              Remove selected product
                products_list.remove(p);

//              Rewrite products file
                try {
                    FileWriter p_writer = new FileWriter("items.txt");
                    for (Product p2 : products_list) {
                        p_writer.append(p2.getpID()).append(",").append(p2.getpName()).append(",").append(String.valueOf
                                (p2.getpPrice())).append(",").append(p2.getpCategory()).append(",").append(p.
                                getpDescriptions()).append("\n");
                    }
                    p_writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Product deleted.");
                product_found = true;
                break;
            }
        }
        if (!product_found) {
            System.out.println("No products with such ID found.");
        }
    }

    static void viewDetailedProduct() {
//      Ask user to input desired product's ID
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input product ID: ");
        String detail_pID = scanner.nextLine();
        boolean product_found = false;

        for (Product p : products_list) {
            if (Objects.equals(p.getpID(), detail_pID)) {
//              Print product's detailed info
                p.printDetailedInfo();
                product_found = true;
                break;
            }
        }
        if (!product_found) {
            System.out.println("No products with such ID found.");
        }
    }

    static void searchByCategory() {
//      Ask user to input desired category
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input category: ");
        String search_category = scanner.nextLine();
        boolean category_found = false;

//      Print all products of input category
        System.out.printf("%-10s %-25s %-20s\n", "ID", "Name", "Price");
        for (Product p : products_list) {
            if (Objects.equals(p.getpCategory(), search_category)) {
                p.printBasicInfo();
                category_found = true;
            }
        }

        if (!category_found) {
            System.out.println("No category found.");
        }
    }

    static void sortByPrice() {
    }

    public static void main(String[] args) {
//      Import products info from txt file to program
        try {
            File p_file = new File("items.txt");
            Scanner p_reader = new Scanner(p_file);
            while (p_reader.hasNextLine()) {
                String data = p_reader.nextLine();
                if (!data.isEmpty()) {
                    String[] p_data = data.split(",");
                    products_list.add(new Product(p_data[0], p_data[1], Integer.parseInt(p_data[2]), p_data[3], p_data[4]));
                }
            }
            p_reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find items file.");
            throw new RuntimeException(e);
        }

//        listAllProducts();
//        addProduct();
//        rmvProduct();
//        changePrice();
//        viewDetailedProduct();
//        searchByCategory();
    }
}
