package Model;
import java.util.Date;

public class User {
    private int id;
    private String fullname;
    private String address;
    private String phoneNumber;
    private String email;
    private int roleId;
    private Date dateOfBirth;
    private String userName;
    private String password;
    private String active;
    private int googleAccountId;
    private int facebookAccountId;

    public User(int id, String fullname, String address, String phoneNumber, String email, int roleId, java.sql.Date dateOfBirth, String userName, String password, String active, int googleAccountId, int facebookAccountId) {
        this.id = id;
        this.fullname = fullname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roleId = roleId;
        this.dateOfBirth = dateOfBirth;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.googleAccountId = googleAccountId;
        this.facebookAccountId = facebookAccountId;
    }

    public User(String nguyenThiThao, String number) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getGoogleAccountId() {
        return googleAccountId;
    }

    public void setGoogleAccountId(int googleAccountId) {
        this.googleAccountId = googleAccountId;
    }

    public int getFacebookAccountId() {
        return facebookAccountId;
    }

    public void setFacebookAccountId(int facebookAccountId) {
        this.facebookAccountId = facebookAccountId;
    }
}
