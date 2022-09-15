package Shop;

public class User {
    private String cID;
    private String cName;
    private String cAddress;
    private String cPhonenumber;
    private String cUsername;
    private String cPassword;

    private String cType = "Customer";

    private String cMembership = "Bronze";

    private int cTotalSpendings = 0;

    public String getcID() {
        return cID;
    }

    public String getcName() {
        return cName;
    }

    public String getcAddress() {
        return cAddress;
    }

    public String getcPhonenumber() {
        return cPhonenumber;
    }

    public String getcUsername() {
        return cUsername;
    }

    public String getcPassword() {
        return cPassword;
    }


    public String getcType() {
        return cType;
    }


    public String getcMembership() {
        return cMembership;
    }

    public int getcTotalSpendings() {
        return cTotalSpendings;
    }

    public User(String cID, String cName, String cAddress, String cPhonenumber, String cUsername, String cPassword) {
        this.cID = cID;
        this.cName = cName;
        this.cAddress = cAddress;
        this.cPhonenumber = cPhonenumber;
        this.cUsername = cUsername;
        this.cPassword = cPassword;
    }

    public User(String cID, String cName, String cAddress, String cPhonenumber, String cUsername, String cPassword, String cType, String cMembership, int cTotalSpendings) {
        this.cID = cID;
        this.cName = cName;
        this.cAddress = cAddress;
        this.cPhonenumber = cPhonenumber;
        this.cUsername = cUsername;
        this.cPassword = cPassword;
        this.cType = cType;
        this.cMembership = cMembership;
        this.cTotalSpendings = cTotalSpendings;
    }


    void printUserInfo() {
        System.out.printf("%-10s %-25s %-20s %-15s %-35s\n", "ID", "Name", "Address", "Phonenumber", "Username", "Password", "Type","Membership", "Total Spendings");
        System.out.printf("%-10s %-25s %-20s %-15s %-35s\n", cID, cName, cAddress, cPhonenumber, cUsername, cPassword, cType, cMembership, cTotalSpendings + "VND");
    }




}
