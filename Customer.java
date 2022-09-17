import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Customer extends User {
    private final String cID;
    private final String cName;
    private final String cAddress;
    private final String cPhone;
    private String cMembership = "Bronze";
    private final String cUsername;
    private final String cPassword;
    private int cTotalSpending = 0;

    public String getcID() {
        return cID;
    }

    public String getcName() {
        return cName;
    }

    public String getcAddress() {
        return cAddress;
    }

    public String getcPhone() {
        return cPhone;
    }

    public String getcMembership() {
        return cMembership;
    }

    @Override
    public String getUsername() {
        return cUsername;
    }

    @Override
    public String getPassword() {
        return cPassword;
    }

    public int getcTotalSpending() {
        return cTotalSpending;
    }

    public Customer(String cID, String cName, String cAddress, String cPhone, String cUsername, String cPassword) {
        this.cID = cID;
        this.cName = cName;
        this.cAddress = cAddress;
        this.cPhone = cPhone;
        this.cUsername = cUsername;
        this.cPassword = cPassword;
    }

    public Customer(String cID, String cName, String cAddress, String cPhone, String cMembership, String cUsername, String cPassword, int cTotalSpending) {
        this.cID = cID;
        this.cName = cName;
        this.cAddress = cAddress;
        this.cPhone = cPhone;
        this.cMembership = cMembership;
        this.cUsername = cUsername;
        this.cPassword = cPassword;
        this.cTotalSpending = cTotalSpending;
    }

    void updateTotalSpending(int oPrice) {
        cTotalSpending = cTotalSpending + oPrice;
        if (cTotalSpending > 25000000) {
            cMembership = "Platinum";
        } else if (cTotalSpending > 10000000) {
            cMembership = "Gold";
        } else if (cTotalSpending > 5000000) {
            cMembership = "Silver";
        }
    }

    void viewMyInfo() {
        System.out.println("ID: " + cID);
        System.out.println("Username: " + cUsername);
        System.out.println("Password: " + cPassword);
        System.out.println("Name: " + cName);
        System.out.println("Address: " + cAddress);
        System.out.println("Phone: " + cPhone);
        System.out.println("Total spending: " + cTotalSpending + " VNĐ");
        System.out.println("Membership: " + cMembership);
        System.out.println();
    }

    void createOrder() {
//      Generate unique order ID
        int temp_oID = Main.orders_list.size() + 1;
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

            for (Product p : Main.products_list) {
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

//      Applying membership discount
        o.applyDiscount(cMembership);

//      Add order to ArrayList
        Main.orders_list.add(o);

//      Append new order info to file
        try {
            FileWriter o_writer = new FileWriter("orders.txt", true);
//          Create a temporary ArrayList to append only product ID in place of products
            ArrayList<String> temp_pID_list = new ArrayList<>();
            for (Product p : o.getoProducts()) {
                temp_pID_list.add(p.getpID());
            }
            String orderString = o.getoID() + "|" + o.getcID() + "|" + temp_pID_list + "|" + o.getoProduct_quantity().
                    toString() + "|" + o.getoPrice() + "|" + o.getoStatus() + "\n";
            o_writer.append(orderString);
            o_writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Order placed!");
        System.out.println();
    }

    void viewMyOrder() {
//      Print a simplified list of orders made by the customer
        System.out.println("Your orders:");
        System.out.printf("%-10s %-25s\n", "Order ID", "Price");
        for (Order o : Main.orders_list) {
            if (Objects.equals(o.getcID(), cID)) {
                o.printBasicInfoOrder();
            }
        }

//      Ask the customer to input desired order's ID
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input order ID: ");
        String view_oID = scanner.nextLine();
        boolean order_found = false;

        for (Order o : Main.orders_list) {
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

    @Override
    void menu() {
        Scanner select = new Scanner(System.in);
        System.out.println("1. My information");
        System.out.println("2. List all items");
        System.out.println("3. View an item's details");
        System.out.println("4. Search by category");
        System.out.println("5. Sort by price");
        System.out.println("6. Create order");
        System.out.println("7. View my orders' information");
        System.out.println("8. Logout");
        System.out.println("0. Exit");
        String option = select.nextLine();

        if (Objects.equals(option, "1")) {
            viewMyInfo();
        } else if (Objects.equals(option, "2")) {
            listAllProducts();
        } else if (Objects.equals(option, "3")) {
            listAllProducts();
            viewDetailedProduct();
        } else if (Objects.equals(option, "4")) {
            listAllProducts();
            searchByCategory();
        } else if (Objects.equals(option, "5")) {
            sortByPrice();
        } else if (Objects.equals(option, "6")) {
            listAllProducts();
            createOrder();
        } else if (Objects.equals(option, "7")) {
            viewMyOrder();
        } else if (Objects.equals(option, "8")) {
            Main.logged_in_as = "user";
        } else if (Objects.equals(option, "0")) {
            Main.logged_in_as = "exit";
        } else {
            System.out.println("Invalid input");
        }
    }
}
