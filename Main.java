import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    //  Create ArrayList of products
    static ArrayList<Product> products_list = new ArrayList<>();
    static ArrayList<Order> orders_list = new ArrayList<>();

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
//          Also restrict input to not contain '|' as the character is used to divide product info
            if (pName.indexOf('|') == -1 && !name_exists) {
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
//      Restrict input to not contain '|'
        while (true) {
            pCategory = scanner.nextLine();
            if (pCategory.indexOf('|') == -1) {
                break;
            }
            System.out.print("That is not a valid category. Please try again: ");
        }

//      Input description
        System.out.print("Input new product's descriptions: ");
        String pDescriptions;
//      Restrict input to not contain '|'
        while (true) {
            pDescriptions = scanner.nextLine();
            if (pDescriptions.indexOf('|') == -1) {
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
            p_writer.append(p.getpID()).append("|").append(p.getpName()).append("|").append(String.valueOf(p.getpPrice()
            )).append("|").append(p.getpCategory()).append("|").append(p.getpDescriptions()).append("\n");
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
                        p_writer.append(p2.getpID()).append("|").append(p2.getpName()).append("|").append(String.valueOf
                                (p2.getpPrice())).append("|").append(p2.getpCategory()).append("|").append(p.
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
                        p_writer.append(p2.getpID()).append("|").append(p2.getpName()).append("|").append(String.valueOf
                                (p2.getpPrice())).append("|").append(p2.getpCategory()).append("|").append(p.
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
        ArrayList<Product> sortedList = new ArrayList<>(products_list);
        sortedList.sort(Comparator.comparingInt(Product::getpPrice));
        System.out.printf("%-10s %-25s %-20s\n", "ID", "Name", "Price");
        for (Product p : sortedList) {
            p.printBasicInfo();
        }
    }

    static void listAllOrders() {
        for (Order o : orders_list) {
            o.adminPrintOrder();
        }
    }

    static void createOrder(String oID, String cID) {
//      Create a new order
        Order o = new Order(oID, cID);

        Scanner scanner = new Scanner(System.in);
        while (true) {
//          Ask user to input desired product's ID
            System.out.print("Input product ID (type 'Done' to finish order): ");
            String order_pID = scanner.nextLine();
//          Finish the order when the customer has done ordering
            if (Objects.equals(order_pID, "Done")) {
                break;
            }
            boolean product_found = false;

            for (Product p : products_list) {
                if (Objects.equals(p.getpID(), order_pID)) {
//                  Add product to order
                    o.orderAddProduct(p);
                    product_found = true;
                    break;
                }
            }
            if (!product_found) {
                System.out.println("No products with such ID found.");
            }
        }
//      Append new order info to file
        try {
            FileWriter o_writer = new FileWriter("orders.txt", true);
//          Create a temporary ArrayList to append only product ID in place of products
            ArrayList<String> temp_pID_list = new ArrayList<>();
            for (Product p : o.getoProducts()) {
                temp_pID_list.add(p.getpID());
            }
            o_writer.append(o.getoID()).append("|").append(o.getcID()).append("|").append(String.valueOf(temp_pID_list))
                    .append("|").append(o.getoProduct_quantity().toString()).append("|").append(String.valueOf(o.
                            getoPrice())).append("|").append(o.getoStatus()).append("\n");
            o_writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        o.customerPrintOrder();
    }

    public static void main(String[] args) {
//      Import products info from txt file to program
        try {
            File p_file = new File("items.txt");
            Scanner p_reader = new Scanner(p_file);
            while (p_reader.hasNextLine()) {
                String data = p_reader.nextLine();
                if (!data.isEmpty()) {
                    String[] p_data = data.split("\\|");
                    products_list.add(new Product(p_data[0], p_data[1], Integer.parseInt(p_data[2]), p_data[3], p_data[4]));
                }
            }
            p_reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find items file.");
            throw new RuntimeException(e);
        }
//      Import orders info from txt file to program
        try {
            File o_file = new File("orders.txt");
            Scanner o_reader = new Scanner(o_file);
            while (o_reader.hasNextLine()) {
                String data = o_reader.nextLine();
                if (!data.isEmpty()) {
                    String[] o_data = data.split("\\|");
//                  Create temporary ArrayList to import Products from their ID
                    o_data[2] = o_data[2].substring(1, o_data[2].length() - 1);
                    String[] temp_pID_import_list = o_data[2].split(",");
                    for (int i = 0; i < temp_pID_import_list.length; i++) {
                        temp_pID_import_list[i] = temp_pID_import_list[i].trim();
                    }
//                  Append products to temporary ArrayList to be imported
                    ArrayList<Product> temp_products_list = new ArrayList<>();
                    for (String temp_pID : temp_pID_import_list) {
                        boolean product_found = false;
                        for (Product p : products_list) {
                            if (Objects.equals(p.getpID(), temp_pID)) {
                                temp_products_list.add(p);
                                product_found = true;
                                break;
                            }
                        }
                        if (!product_found) {
                            System.out.println("Nonexistent or removed product found in saved orders.");
                        }
                    }
//                  Create temporary ArrayList to import products' quantities
                    o_data[3] = o_data[3].substring(1, o_data[3].length() - 1);
                    String[] temp_pQuantity_str_import_list = o_data[3].split(",");
                    for (int i = 0; i < temp_pQuantity_str_import_list.length; i++) {
                        temp_pQuantity_str_import_list[i] = temp_pQuantity_str_import_list[i].trim();
                    }
//                  Append products' quantities to temporary ArrayList to be imported
                    ArrayList<Integer> temp_pQuantity_int_import_list = new ArrayList<>();
                    for (String temp_pQuantity : temp_pQuantity_str_import_list) {
                        temp_pQuantity_int_import_list.add(Integer.parseInt(temp_pQuantity));
                    }

                    orders_list.add(new Order(o_data[0], o_data[1], temp_products_list, temp_pQuantity_int_import_list,
                            Integer.parseInt(o_data[4]), o_data[5]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find orders file.");
            throw new RuntimeException(e);
        }

//        listAllProducts();
//        addProduct();
//        rmvProduct();
//        changePrice();
//        viewDetailedProduct();
//        searchByCategory();
//        sortByPrice();
//        listAllOrders();

//        createOrder("O-920", "C-920");
    }
}
