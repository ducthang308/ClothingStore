package DTO;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UsersDTO {
    @SerializedName("fullname")
    private String fullName;
    @SerializedName("phone_number")
    private String phoneNumber;
    private String address;
    private String password;
    private String email;
    @SerializedName("retype_pass")
    private String retypePass;
    @SerializedName("date_of_birth")
    private String dateOfBirth; // Change to String
    private int facebookAccountId;
    private int googleAccountId;
    private Long roleId;

    public UsersDTO(String fullName, String phoneNumber, String address, String password, String email, String retypePass, String dateOfBirth) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.email = email;
        this.retypePass = retypePass;
        this.dateOfBirth = dateOfBirth;
    }
    public UsersDTO(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRetypePass() {
        return retypePass;
    }

    public void setRetypePass(String retypePass) {
        this.retypePass = retypePass;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getFacebookAccountId() {
        return facebookAccountId;
    }

    public void setFacebookAccountId(int facebookAccountId) {
        this.facebookAccountId = facebookAccountId;
    }

    public int getGoogleAccountId() {
        return googleAccountId;
    }

    public void setGoogleAccountId(int googleAccountId) {
        this.googleAccountId = googleAccountId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}