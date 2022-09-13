import java.util.ArrayList;

public class Order {
    private String oID;
    private String cID;
    private ArrayList<Product> oProducts = new ArrayList<>();
    private ArrayList<Integer> oProduct_amount = new ArrayList<>();

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

    public ArrayList<Integer> getoProduct_amount() {
        return oProduct_amount;
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

    void orderAddProduct(Product p) {
        if (!oProducts.contains(p)) {
            oProducts.add(p);
            oProduct_amount.add(1);
        } else {
            oProduct_amount.set(oProducts.indexOf(p), oProduct_amount.get(oProducts.indexOf(p)) + 1);
        }
        oPrice = oPrice + p.getpPrice();
    }

    void customerPrintOrder(){
        System.out.println("Order ID: " + oID + "; Customer ID: " + cID);
        System.out.println("Product(s) ordered:");
        for (int i = 0; i<oProducts.size(); i++){
            System.out.printf("%-25s %-5s\n", oProducts.get(i).getpName(), oProduct_amount.get(i));
        }
        System.out.println("Total price: " + oPrice + " VNĐ; Status: " + oStatus);
    }

    void adminPrintOrder(){
        customerPrintOrder();
        System.out.println("---------------------------------------------------------------------------");
    }
}
