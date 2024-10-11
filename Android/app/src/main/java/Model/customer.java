package Model;

public class customer {
    private String fullname;
    private String phoneNumber;

    public customer(String fullname, String phoneNumber) {
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public customer() {
    }
}
