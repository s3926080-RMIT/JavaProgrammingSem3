import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    //    Create ArrayList of products
    static ArrayList<Product> products_list = new ArrayList<>();

    public static void main(String[] args) {
//        Import products info from txt file to program
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

    }
}
