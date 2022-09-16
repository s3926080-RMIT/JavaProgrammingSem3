public class Product {
    private final String pID;
    private final String pName;
    private int pPrice;
    private final String pCategory;
    private final String pDescriptions;

    public String getpID() {
        return pID;
    }

    public String getpName() {
        return pName;
    }

    public int getpPrice() {
        return pPrice;
    }

    public String getpCategory() {
        return pCategory;
    }

    public String getpDescriptions() {
        return pDescriptions;
    }

    public void setpPrice(int pPrice) {
        this.pPrice = pPrice;
    }

    public Product(String pID, String pName, int pPrice, String pCategory, String pDescriptions) {
        this.pID = pID;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pCategory = pCategory;
        this.pDescriptions = pDescriptions;
    }

    void printDetailedInfo() {
        System.out.printf("%-10s %-25s %-20s %-15s %-35s\n", "ID", "Name", "Price", "Category", "Descriptions");
        System.out.printf("%-10s %-25s %-20s %-15s %-35s\n", pID, pName, pPrice + " VNĐ", pCategory, pDescriptions);
    }

    void printBasicInfo() {
        System.out.printf("%-10s %-25s %-20s\n", pID, pName, pPrice + " VNĐ");
    }
}
