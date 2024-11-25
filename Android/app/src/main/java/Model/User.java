package Model;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("address")
    private String address;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("email")
    private String email;

    @SerializedName("role_id")
    private Roles roleId;

    @SerializedName("date_of_birth")
    private Date dateOfBirth; // Nếu cần, có thể đổi thành String

    @SerializedName("password")
    private String password;

    @SerializedName("active")
    private String active;

    @SerializedName("google_account_id")
    private int googleAccountId;

    @SerializedName("facebook_account_id")
    private int facebookAccountId;

    public User(int id, String fullname, String address, String phoneNumber, String email, Roles roleId, Date dateOfBirth, String userName, String password, String active, int googleAccountId, int facebookAccountId) {
        this.id = id;
        this.fullname = fullname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roleId = roleId;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.active = active;
        this.googleAccountId = googleAccountId;
        this.facebookAccountId = facebookAccountId;
    }



    public long getId() {
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

    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", dateOfBirth=" + dateOfBirth +
                ", password='" + password + '\'' +
                ", active='" + active + '\'' +
                ", googleAccountId=" + googleAccountId +
                ", facebookAccountId=" + facebookAccountId +
                '}';
    }
}