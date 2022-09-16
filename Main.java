import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    //  Create ArrayList of products, orders and users
    static ArrayList<Product> products_list = new ArrayList<>();
    static ArrayList<Order> orders_list = new ArrayList<>();
    static ArrayList<User> users_list = new ArrayList<>();
    //  Refresh the ArrayList of executed orders when the program starts
    static ArrayList<Order> orders_exec_list = new ArrayList<>();
    static String logged_in_as;

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
            o_reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find orders file.");
            throw new RuntimeException(e);
        }
//      Import users from txt file to program
        try {
            File u_file = new File("users.txt");
            Scanner u_reader = new Scanner(u_file);
            while (u_reader.hasNextLine()) {
                String data = u_reader.nextLine();
                if (!data.isEmpty()) {
                    String[] u_data = data.split("\\|");
                    if (Objects.equals(u_data[5], "admin")) {
                        users_list.add(new Admin(u_data[5], u_data[6]));
                    } else {
                        users_list.add(new Customer(u_data[0], u_data[1], u_data[2], u_data[3], u_data[4], u_data[5],
                                u_data[6], Integer.parseInt(u_data[7])));
                    }
                }
            }
            u_reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find users file.");
            throw new RuntimeException(e);
        }
        User guest_user = new User();
        logged_in_as = "user";
        while (true) {
            if (Objects.equals(logged_in_as, "user")) {
                guest_user.menu();
            } else if (Objects.equals(logged_in_as, "admin")) {
                for (User u : users_list) {
                    if (Objects.equals(u.getUsername(), logged_in_as)) {
                        u.menu();
                        break;
                    }
                }
            } else if (Objects.equals(logged_in_as, "exit")) {
                break;
            } else {
                for (User u : users_list) {
                    if (Objects.equals(u.getUsername(), logged_in_as)) {
                        u.menu();
                        break;
                    }
                }
            }
        }
    }
}
