import java.util.ArrayList;

public class Order {
    private final String oID;
    private final String cID;
    private ArrayList<Product> oProducts = new ArrayList<>();
    private ArrayList<Integer> oProduct_quantity = new ArrayList<>();

    private int oPrice = 0;
    private String oStatus = "UNPAID";

    public String getoID() {
        return oID;
    }

    public String getcID() {
        return cID;
    }

    public ArrayList<Product> getoProducts() {
        return oProducts;
    }

    public ArrayList<Integer> getoProduct_quantity() {
        return oProduct_quantity;
    }

    public int getoPrice() {
        return oPrice;
    }

    public String getoStatus() {
        return oStatus;
    }

    public void setoStatus(String oStatus) {
        this.oStatus = oStatus;
    }

    public Order(String oID, String cID) {
        this.oID = oID;
        this.cID = cID;
    }

    public Order(String oID, String cID, ArrayList<Product> oProducts, ArrayList<Integer> oProduct_quantity, int oPrice, String oStatus) {
        this.oID = oID;
        this.cID = cID;
        this.oProducts = oProducts;
        this.oProduct_quantity = oProduct_quantity;
        this.oPrice = oPrice;
        this.oStatus = oStatus;
    }

    void orderAddProduct(Product p) {
        if (!oProducts.contains(p)) {
            oProducts.add(p);
            oProduct_quantity.add(1);
        } else {
            oProduct_quantity.set(oProducts.indexOf(p), oProduct_quantity.get(oProducts.indexOf(p)) + 1);
        }
        oPrice = oPrice + p.getpPrice();
    }

    void customerPrintOrder() {
        System.out.println("Order ID: " + oID + "; Customer ID: " + cID);
        System.out.println("Product(s) ordered:");
        System.out.printf("%-25s %-5s\n", "Name", "Quantity");
        for (int i = 0; i < oProducts.size(); i++) {
            System.out.printf("%-25s %-5s\n", oProducts.get(i).getpName(), oProduct_quantity.get(i));
        }
        System.out.println("Total price: " + oPrice + " VNĐ; Status: " + oStatus);
    }

    void adminPrintOrder() {
        customerPrintOrder();
        System.out.println("---------------------------------------------------------------------------");
    }

    void printBasicInfoOrder(){
        System.out.printf("%-10s %-25s\n", oID, oPrice + " VNĐ");
    }
}
