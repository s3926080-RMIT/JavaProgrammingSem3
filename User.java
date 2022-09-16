import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

public class User {
    public String getUsername() {
        return null;
    }

    public String getPassword() {
        return null;
    }

    void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String login_username = scanner.nextLine();
        boolean user_found = false;
        for (User u : Main.users_list) {
            if (Objects.equals(u.getUsername(), login_username)) {
                user_found = true;
                System.out.print("Enter password: ");
                String login_password = scanner.nextLine();
                if (Objects.equals(u.getPassword(), login_password)) {
                    System.out.println("Login successful!");
                    Main.logged_in_as = login_username;
                } else {
                    System.out.println("Incorrect password.");
                    System.out.println();
                }
            }
        }
        if (!user_found) {
            System.out.println("No account with such username found.");
            System.out.println();
        }
    }

    void registerCustomer() {
//      Ask user to input their name
        Scanner scanner = new Scanner(System.in);

//      Input username
        System.out.print("Enter new username: ");
        String cUsername;
        while (true) {
            cUsername = scanner.nextLine();
//          Restrict input to avoid duplicate usernames
            boolean name_exists = false;
            for (User u : Main.users_list) {
                if (Objects.equals(cUsername, u.getUsername())) {
                    name_exists = true;
                    break;
                }
            }
//          Also restrict input to not contain '|' as the character is used to divide user info
            if (cUsername.indexOf('|') == -1 && !name_exists) {
                break;
            }
            System.out.print("That is not a valid username. Please try again: ");
        }

//      Input password
        System.out.print("Enter new password: ");
        String cPassword;
//      Restrict input to not contain '|'
        while (true) {
            cPassword = scanner.nextLine();
            if (cPassword.indexOf('|') == -1) {
                break;
            }
            System.out.print("That is not a valid password. Please try again: ");
        }

//      Input full name
        System.out.print("Enter your full name: ");
        String cName;
//      Restrict input to not contain '|'
        while (true) {
            cName = scanner.nextLine();
            if (cName.indexOf('|') == -1) {
                break;
            }
            System.out.print("That is not a valid name. Please try again: ");
        }

//      Input address
        System.out.print("Enter your address: ");
        String cAddress;
//      Restrict input to not contain '|'
        while (true) {
            cAddress = scanner.nextLine();
            if (cAddress.indexOf('|') == -1) {
                break;
            }
            System.out.print("That is not a valid address. Please try again: ");
        }

//      Input phone number
        System.out.print("Enter your phone number: ");
        String cPhoneNum;
//      Restrict input to only digits
        while (true) {
            cPhoneNum = scanner.nextLine();
            if (cPhoneNum.matches("^[0-9]+$")) {
                break;
            }
            System.out.print("That is not a valid phone number. Please try again: ");
        }

//      Generate unique customer ID
        int temp_cID = Main.users_list.size() + 2;
        String temp_cID_string;
        if (temp_cID < 10) {
            temp_cID_string = "C-00" + temp_cID;
        } else if (temp_cID < 100) {
            temp_cID_string = "C-0" + temp_cID;
        } else {
            temp_cID_string = "C-" + temp_cID;
        }

//      Add customer to ArrayList
        Customer c = new Customer(temp_cID_string, cName, cAddress, cPhoneNum, cUsername, cPassword);
        Main.users_list.add(c);

//      Append new customer info to file
        try {
            FileWriter c_writer = new FileWriter("users.txt");
            String newCustomer = c.getcID() + "|" + c.getcName() + "|" + c.getcAddress() + "|" + c.getcPhone() + "|" + c
                    .getcMembership() + "|" + c.getUsername() + "|" + c.getPassword() + "|" + c.getcTotalSpending() + "\n";
            c_writer.append(newCustomer);
            c_writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Registry successful!");
        System.out.println();
    }

    void listAllProducts() {
        System.out.printf("%-10s %-25s %-20s\n", "ID", "Name", "Price");
        for (Product p : Main.products_list) {
            p.printBasicInfo();
        }
        System.out.println();
    }

    void viewDetailedProduct() {
//      Ask user to input desired product's ID
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input product ID: ");
        String detail_pID = scanner.nextLine();
        boolean product_found = false;

        for (Product p : Main.products_list) {
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

    void searchByCategory() {
//      Ask user to input desired category
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input category: ");
        String search_category = scanner.nextLine();
        boolean category_found = false;

//      Print all products of input category
        System.out.printf("%-10s %-25s %-20s\n", "ID", "Name", "Price");
        for (Product p : Main.products_list) {
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

    void sortByPrice() {
        ArrayList<Product> sortedList = new ArrayList<>(Main.products_list);
        sortedList.sort(Comparator.comparingInt(Product::getpPrice));
        System.out.printf("%-10s %-25s %-20s\n", "ID", "Name", "Price");
        for (Product p : sortedList) {
            p.printBasicInfo();
        }
        System.out.println();
    }

    void menu() {
        Scanner select = new Scanner(System.in);
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. List all items");
        System.out.println("4. View an item's details");
        System.out.println("5. Search by category");
        System.out.println("6. Sort by price");
        System.out.println("0. Exit");
        String option = select.nextLine();

        if (Objects.equals(option, "1")) {
            login();
        } else if (Objects.equals(option, "2")) {
            registerCustomer();
        } else if (Objects.equals(option, "3")) {
            listAllProducts();
        } else if (Objects.equals(option, "4")) {
            listAllProducts();
            viewDetailedProduct();
        } else if (Objects.equals(option, "5")) {
            listAllProducts();
            searchByCategory();
        } else if (Objects.equals(option, "6")) {
            sortByPrice();
        } else if (Objects.equals(option, "0")) {
            Main.logged_in_as = "exit";
        } else {
            System.out.println("Invalid input");
        }
    }
}
