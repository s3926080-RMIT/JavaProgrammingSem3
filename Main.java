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
    //  Refresh the ArrayList of executed orders when the program starts
    static ArrayList<Order> orders_exec_list = new ArrayList<>();

    static void listAllProducts() {
        System.out.printf("%-10s %-25s %-20s\n", "ID", "Name", "Price");
        for (Product p : products_list) {
            p.printBasicInfo();
        }
        System.out.println();
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
        int temp_pID = products_list.size() + 1;
        String temp_pID_string;
        if (temp_pID < 10) {
            temp_pID_string = "P-00" + temp_pID;
        } else if (temp_pID < 100) {
            temp_pID_string = "P-0" + temp_pID;
        } else {
            temp_pID_string = "P-" + temp_pID;
        }

//      Add product to ArrayList
        Product p = new Product(temp_pID_string, pName, pPrice, pCategory, pDescriptions);
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

        System.out.println("Product added successfully.");
        System.out.println();
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
                                (p2.getpPrice())).append("|").append(p2.getpCategory()).append("|").append(p2.
                                getpDescriptions()).append("\n");
                    }
                    p_writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Product price changed.");
                System.out.println();
                product_found = true;
                break;
            }
        }
        if (!product_found) {
            System.out.println("No products with such ID found.");
            System.out.println();
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
                                (p2.getpPrice())).append("|").append(p2.getpCategory()).append("|").append(p2.
                                getpDescriptions()).append("\n");
                    }
                    p_writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Product removed.");
                System.out.println();
                product_found = true;
                break;
            }
        }
        if (!product_found) {
            System.out.println("No products with such ID found.");
            System.out.println();
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
                System.out.println();
                product_found = true;
                break;
            }
        }
        if (!product_found) {
            System.out.println("No products with such ID found.");
            System.out.println();
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
        System.out.println();

        if (!category_found) {
            System.out.println("No such category found.");
            System.out.println();
        }
    }

    static void sortByPrice() {
        ArrayList<Product> sortedList = new ArrayList<>(products_list);
        sortedList.sort(Comparator.comparingInt(Product::getpPrice));
        System.out.printf("%-10s %-25s %-20s\n", "ID", "Name", "Price");
        for (Product p : sortedList) {
            p.printBasicInfo();
        }
        System.out.println();
    }

    static void listAllOrders() {
        for (Order o : orders_list) {
            o.adminPrintOrder();
        }
        System.out.println();
    }

    static void createOrder(String cID) {
//      Generate unique order ID
        int temp_oID = orders_list.size() + 1;
        String temp_oID_string;
        if (temp_oID < 10) {
            temp_oID_string = "O-00" + temp_oID;
        } else if (temp_oID < 100) {
            temp_oID_string = "O-0" + temp_oID;
        } else {
            temp_oID_string = "O-" + temp_oID;
        }

//      Create a new order
        Order o = new Order(temp_oID_string, cID);

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

//      Add order to ArrayList
        orders_list.add(o);

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

        System.out.println("Order placed!");
        System.out.println();
    }

    static void changeOrderStatus() {
//      Ask admin to input ID of order to be executed
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input order ID: ");
        String change_oID = scanner.nextLine();
        boolean order_found = false;

        for (Order o : orders_list) {
            if (Objects.equals(o.getoID(), change_oID)) {
//              Check if the selected order has been paid
                if (Objects.equals(o.getoStatus(), "UNPAID")) {
                    o.customerPrintOrder();
//                  Restrict input to only "Y" or "N" (case-insensitive)
                    String confirmation;
                    while (true) {
//                      Confirmation message
                        System.out.println("Change status to 'PAID'? (Y/N)");
                        confirmation = scanner.nextLine();
                        if (Objects.equals(confirmation, "Y") || Objects.equals(confirmation, "y")) {
                            o.setoStatus("PAID");
//                          Add the executed order to the ArrayList of orders executed in the day
                            orders_exec_list.add(o);

//                          Rewrite order info to file
                            try {
                                FileWriter o_writer = new FileWriter("orders.txt");
                                for (Order o2 : orders_list) {
//                                  Create a temporary ArrayList to append only product ID in place of products
                                    ArrayList<String> temp_pID_list = new ArrayList<>();
                                    for (Product p : o2.getoProducts()) {
                                        temp_pID_list.add(p.getpID());
                                    }
                                    o_writer.append(o2.getoID()).append("|").append(o2.getcID()).append("|").append(String.
                                                    valueOf(temp_pID_list)).append("|").append(o2.getoProduct_quantity().toString())
                                            .append("|").append(String.valueOf(o2.getoPrice())).append("|").append(o2.
                                                    getoStatus()).append("\n");
                                }
                                o_writer.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("Order status changed to 'PAID'");
                            System.out.println();
                            break;
                        } else if (Objects.equals(confirmation, "N") || Objects.equals(confirmation, "n")) {
//                          Cancel the action if the admin wants to
                            System.out.println("Action cancelled.");
                            System.out.println();
                            break;
                        }
                        System.out.println("Invalid input.");
                        System.out.println();
                    }
                } else {
                    System.out.println("This order has already been paid.");
                    System.out.println();
                }
                order_found = true;
                break;
            }
        }
        if (!order_found) {
            System.out.println("No orders with such ID found.");
            System.out.println();
        }
    }

    static void calcRevenue() {
        int revenue = 0;
//      Returns the sum of prices of all orders executed
        for (Order o : orders_list) {
            if (Objects.equals(o.getoStatus(), "PAID")) {
                revenue = revenue + o.getoPrice();
            }
        }
        System.out.println("Total revenue: " + revenue + " VNĐ");
        System.out.println();
    }

    static void listExecOrders() {
//      Print info of orders executed in the day
        for (Order o : orders_exec_list) {
            o.adminPrintOrder();
        }
        System.out.println();
    }

    static void viewMyOrder(String cID) {
//      Print a simplified list of orders made by the customer
        System.out.println("Your orders:");
        System.out.printf("%-10s %-25s\n", "Order ID", "Price");
        for (Order o : orders_list) {
            if (Objects.equals(o.getcID(), cID)) {
                o.printBasicInfoOrder();
            }
        }

//      Ask the customer to input desired order's ID
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input order ID: ");
        String view_oID = scanner.nextLine();
        boolean order_found = false;

        for (Order o : orders_list) {
            if (Objects.equals(o.getoID(), view_oID)) {
//              Print the order's info
                o.customerPrintOrder();
                System.out.println();
                order_found = true;
                break;
            }
        }
        if (!order_found) {
            System.out.println("No orders with such ID found.");
            System.out.println();
        }
    }

    static void listAllOrdersByCID() {
//      Ask the admin to input customer ID
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input customer ID: ");
        String listOrder_cID = scanner.nextLine();
        boolean order_found = false;

        for (Order o : orders_list) {
            if (Objects.equals(o.getcID(), listOrder_cID)) {
//              Print orders' info
                o.adminPrintOrder();
                order_found = true;
            }
        }
        System.out.println();
        if (!order_found) {
            System.out.println("No orders with such Customer ID found.");
            System.out.println();
        }
    }

    //  Tuan's part
    static void menuGuestUser() {
        Scanner select = new Scanner(System.in);
        while (true) {
            System.out.println("1. List all items");
            System.out.println("2. View an item's details");
            System.out.println("3. Search by category");
            System.out.println("4. Sort by price");
            System.out.println("0. Exit");
            int option = select.nextInt();

            if (option == 1) {
                listAllProducts();
            } else if (option == 2) {
                viewDetailedProduct();
            } else if (option == 3) {
                searchByCategory();
            } else if (option == 4) {
                sortByPrice();
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    static void menuMember() {
        Scanner select = new Scanner(System.in);
        while (true) {
            System.out.println("1. My information");
            System.out.println("2. List all items");
            System.out.println("3. View an item's details");
            System.out.println("4. Search by category");
            System.out.println("5. Sort by price");
            System.out.println("6. Create order");
            System.out.println("7. View my orders' information");
            System.out.println("8. Logout");
            System.out.println("0. Exit");
            int option = select.nextInt();

            if (option == 1) {
                System.out.println("Feature not available");
            } else if (option == 2) {
                listAllProducts();
            } else if (option == 3) {
                viewDetailedProduct();
            } else if (option == 4) {
                searchByCategory();
            } else if (option == 5) {
                sortByPrice();
            } else if (option == 6) {
                createOrder("C-920");
            } else if (option == 7) {
                viewMyOrder("C-920");
            } else if (option == 8) {
                System.out.println("Feature not available");
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    static void menuAdmin() {
        Scanner select = new Scanner(System.in);
        while (true) {
            System.out.println("1. List all items");
            System.out.println("2. List all members");
            System.out.println("3. List all orders");
            System.out.println("4. Add new product");
            System.out.println("5. Remove product");
            System.out.println("6. Update product price");
            System.out.println("7. Get info on all orders");
            System.out.println("8. Change order status");
            System.out.println("9. Calculate total revenue");
            System.out.println("10. View orders executed today");
            System.out.println("11. Logout");
            System.out.println("0. Exit");
            int option = select.nextInt();

            if (option == 1) {
                listAllProducts();
            } else if (option == 2) {
                System.out.println("Feature not available");
            } else if (option == 3) {
                listAllOrders();
            } else if (option == 4) {
                addProduct();
            } else if (option == 5) {
                rmvProduct();
            } else if (option == 6) {
                changePrice();
            } else if (option == 7) {
                listAllOrdersByCID();
            } else if (option == 8) {
                changeOrderStatus();
            } else if (option == 9) {
                calcRevenue();
            } else if (option == 10) {
                listExecOrders();
            } else if (option == 11) {
                System.out.println("Feature not available");
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    static void menuStarting() {
        Scanner select = new Scanner(System.in);
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Browse items without logging in");
            System.out.println("0. Exit");
            int option = select.nextInt();
            if (option == 1) {
                System.out.println("Feature not available");
            } else if (option == 2) {
                menuGuestUser();
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
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
//        changeOrderStatus();
//        calcRevenue();
//        listExecOrders();
//        listAllOrdersByCID();

//        createOrder("C-920");
//        viewMyOrder("C-920");
        menuStarting();
//        menuMember();
//        menuAdmin();
    }
}
