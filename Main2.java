package Shop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class Main2 {
    // Create ArrayList of Customers
    static ArrayList<User> users_list = new ArrayList<>();

    static void registerCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Name: ");
        String cName;
        while (true) {
            cName = scanner.nextLine();
            if (cName.indexOf('|') == -1) {
                break;
            }
            System.out.print("That is not a valid name. Please try again: ");
        }

        System.out.print("Enter Address: ");
        String cAddress;
        while (true) {
            cAddress = scanner.nextLine();
            if (cAddress.indexOf('|') == -1) {
                break;
            }
            System.out.print("That is not a valid Address. Please try again: ");
        }

        System.out.print("Enter Phonenumber: ");
        String cPhonenumber;
        while (true) {
            cPhonenumber = scanner.nextLine();
            if (cPhonenumber.indexOf('|') == -1) {
                break;
            }
            System.out.print("That is not a valid phonenumber. Please try again: ");
        }

        System.out.print("Enter Username: ");
        String cUsername;
        while (true) {
            cUsername = scanner.nextLine();
            boolean name_exists = false;
            for (User p : users_list) {
                if (Objects.equals(cUsername, p.getcUsername())) {
                    name_exists = true;
                    break;
                }
            }
            if (cUsername.indexOf('|') == -1 && !name_exists) {
                break;
            }
            System.out.print("That is not a valid Username. Please try again: ");
        }

        System.out.print("Enter Password: ");
        String cPassword;
        while (true) {
            cPassword = scanner.nextLine();
            if (cPassword.indexOf('|') == -1) {
                break;
            }
            System.out.print("That is not a valid password. Please try again: ");
        }

      

        int number = users_list.size() + 1;
        String number_string;
        if (number < 10) {
            number_string = "U-00" + number;
        } else if (number < 100) {
            number_string = "U-0" + number;
        } else {
            number_string = "U-" + number;
        }

        User c = new User(number_string, cName, cAddress, cPhonenumber, cUsername, cPassword);
        users_list.add(c);

        try {
            FileWriter c_writer = new FileWriter("customers.txt", true);
            c_writer.append(c.getcID()).append("|").append(c.getcName()).append("|").append(c.getcAddress()).append("|").append(c.getcPhonenumber()).append("|").append(c.getcUsername()).append("|").append(c.getcPassword()).append("|").append(c.getcType()).append("|").append(c.getcMembership()).append("|").append(String.valueOf(c.getcTotalSpendings()
            )).append("\n");
            c_writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main (String[] args) {
        try {
            File c_file = new File("customers.txt");
            Scanner c_reader = new Scanner(c_file);
            while (c_reader.hasNextLine()) {
                String data = c_reader.nextLine();
                if (!data.isEmpty()) {
                    String[] c_data = data.split("\\|");
                    users_list.add(new User(c_data[0], c_data[1], c_data[2], c_data[3], c_data[4], c_data[5], c_data[6]));
                }
            }
            c_reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find items file.");
            throw new RuntimeException(e);
        }

        registerCustomer();


    }
}

