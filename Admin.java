import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Admin extends User {
    private final String aUsername;
    private final String aPassword;

    @Override
    public String getUsername() {
        return aUsername;
    }

    @Override
    public String getPassword() {
        return aPassword;
    }

    public Admin(String aUsername, String aPassword) {
        this.aUsername = aUsername;
        this.aPassword = aPassword;
    }

    void listAllCustomers() {
        System.out.printf("%-10s %-20s %-25s %-30s %-15s %-25s %-10s\n", "ID", "Username", "Name", "Address", "Phone",
                "Total spending", "Membership");
        for (User u : Main.users_list) {
            if (u instanceof Customer) {
                System.out.printf("%-10s %-20s %-25s %-30s %-15s %-25s %-10s\n", ((Customer) u).getcID(), u.
                        getUsername(), ((Customer) u).getcName(), ((Customer) u).getcAddress(), ((Customer) u).getcPhone
                        (), ((Customer) u).getcTotalSpending() + " VNĐ", ((Customer) u).getcMembership());
            }
        }
        System.out.println();
    }

    void listAllOrders() {
        for (Order o : Main.orders_list) {
            o.adminPrintOrder();
        }
        System.out.println();
    }

    void addProduct() {
//      Ask admin to input new product info
        Scanner scanner = new Scanner(System.in);

//      Input name
        System.out.print("Input new product name: ");
        String pName;
        while (true) {
            pName = scanner.nextLine();
//          Restrict input to avoid repeated product names
            boolean name_exists = false;
            for (Product p : Main.products_list) {
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

//      Input descriptions
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
        int temp_pID = Main.products_list.size() + 1;
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
        Main.products_list.add(p);

//      Append new product info to file
        try {
            FileWriter p_writer = new FileWriter("items.txt", true);
            String newProduct = p.getpID() + "|" + p.getpName() + "|" + p.getpPrice() + "|" + p.getpCategory() + "|" + p
                    .getpDescriptions() + "\n";
            p_writer.append(newProduct);
            p_writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Product added successfully.");
        System.out.println();
    }

    void rmvProduct() {
//      Ask admin to input ID of product to be removed
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input product ID: ");
        String rmv_pID = scanner.nextLine();
        boolean product_found = false;

        for (Product p : Main.products_list) {
            if (Objects.equals(p.getpID(), rmv_pID)) {
//              Remove selected product
                Main.products_list.remove(p);

//              Rewrite products file
                try {
                    FileWriter p_writer = new FileWriter("items.txt");
                    for (Product p2 : Main.products_list) {
                        String productString = p2.getpID() + "|" + p2.getpName() + "|" + p2.getpPrice() + "|" + p2.
                                getpCategory() + "|" + p2.getpDescriptions() + "\n";
                        p_writer.append(productString);
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

    void changePrice() {
//      Ask admin to input ID of product to be repriced
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input product ID: ");
        String change_pID = scanner.nextLine();
        boolean product_found = false;

        for (Product p : Main.products_list) {
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
                    for (Product p2 : Main.products_list) {
                        String productString = p2.getpID() + "|" + p2.getpName() + "|" + p2.getpPrice() + "|" + p2.
                                getpCategory() + "|" + p2.getpDescriptions() + "\n";
                        p_writer.append(productString);
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

    void listAllOrdersByCID() {
//      Ask the admin to input customer ID
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input customer ID: ");
        String listOrder_cID = scanner.nextLine();
        boolean order_found = false;

        for (Order o : Main.orders_list) {
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

    void changeOrderStatus() {
//      Ask admin to input ID of order to be executed
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input order ID: ");
        String change_oID = scanner.nextLine();
        boolean order_found = false;

        for (Order o : Main.orders_list) {
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
                            Main.orders_exec_list.add(o);

//                          Rewrite order info to file
                            try {
                                FileWriter o_writer = new FileWriter("orders.txt");
                                for (Order o2 : Main.orders_list) {
//                                  Create a temporary ArrayList to append only product ID in place of products
                                    ArrayList<String> temp_pID_list = new ArrayList<>();
                                    for (Product p : o2.getoProducts()) {
                                        temp_pID_list.add(p.getpID());
                                    }
                                    String orderString = o2.getoID() + "|" + o2.getcID() + "|" + temp_pID_list + "|" +
                                            o2.getoProduct_quantity().toString() + "|" + o2.getoPrice() + "|" + o2.
                                            getoStatus() + "\n";
                                    o_writer.append(orderString);
                                }
                                o_writer.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

//                          Update the total spending (and membership if needed) of the customer of the order
                            for (User u : Main.users_list) {
                                if (u instanceof Customer && Objects.equals(((Customer) u).getcID(), o.getcID())) {
                                    ((Customer) u).updateTotalSpending(o.getoPrice());
                                    break;
                                }
                            }

//                          Rewrite customer info to file
                            try {
                                FileWriter u_writer = new FileWriter("users.txt");
                                for (User u : Main.users_list) {
                                    if (u instanceof Admin) {
                                        String adminString = "NULL|NULL|NULL|NULL|NULL|" + u.getUsername() + "|" + u.
                                                getPassword() + "|NULL\n";
                                        u_writer.append(adminString);
                                    } else if (u instanceof Customer) {
                                        String customerString = ((Customer) u).getcID() + "|" + ((Customer) u).getcName
                                                () + "|" + ((Customer) u).getcAddress() + "|" + ((Customer) u).getcPhone
                                                () + "|" + ((Customer) u).getcMembership() + "|" + u.getUsername() + "|"
                                                + u.getPassword() + "|" + ((Customer) u).getcTotalSpending() + "\n";
                                        u_writer.append(customerString);
                                    }
                                }
                                u_writer.close();
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

    void calcRevenue() {
        int revenue = 0;
//      Returns the sum of prices of all orders executed
        for (Order o : Main.orders_list) {
            if (Objects.equals(o.getoStatus(), "PAID")) {
                revenue = revenue + o.getoPrice();
            }
        }
        System.out.println("Total revenue: " + revenue + " VNĐ");
        System.out.println();
    }

    void listExecOrders() {
//      Print info of orders executed in the day
        for (Order o : Main.orders_exec_list) {
            o.adminPrintOrder();
        }
        System.out.println();
    }

    @Override
    void menu() {
        Scanner select = new Scanner(System.in);
        System.out.println("1. List all items");
        System.out.println("2. List all members");
        System.out.println("3. List all orders");
        System.out.println("4. Add new product");
        System.out.println("5. Remove product");
        System.out.println("6. Update product price");
        System.out.println("7. Get info on all orders by customer ID");
        System.out.println("8. Change order status");
        System.out.println("9. Calculate total revenue");
        System.out.println("10. View orders executed today");
        System.out.println("11. Logout");
        System.out.println("0. Exit");
        String option = select.nextLine();

        if (Objects.equals(option, "1")) {
            listAllProducts();
        } else if (Objects.equals(option, "2")) {
            listAllCustomers();
        } else if (Objects.equals(option, "3")) {
            listAllOrders();
        } else if (Objects.equals(option, "4")) {
            addProduct();
        } else if (Objects.equals(option, "5")) {
            listAllProducts();
            rmvProduct();
        } else if (Objects.equals(option, "6")) {
            listAllProducts();
            changePrice();
        } else if (Objects.equals(option, "7")) {
            listAllOrdersByCID();
        } else if (Objects.equals(option, "8")) {
            listAllOrders();
            changeOrderStatus();
        } else if (Objects.equals(option, "9")) {
            calcRevenue();
        } else if (Objects.equals(option, "10")) {
            listExecOrders();
        } else if (Objects.equals(option, "11")) {
            Main.logged_in_as = "user";
        } else if (Objects.equals(option, "0")) {
            Main.logged_in_as = "exit";
        } else {
            System.out.println("Invalid input");
        }
    }
}
