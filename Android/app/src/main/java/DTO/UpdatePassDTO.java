package DTO;

import com.google.gson.annotations.SerializedName;

public class UpdatePassDTO {
    @SerializedName("password")
    private String password;

    @SerializedName("retype_pass")
    private String retypePass;

    @SerializedName("new_pass")
    private String newPass;

    public UpdatePassDTO(String password, String retypePass, String newPass) {
        this.retypePass = retypePass;
        this.password = password;
        this.newPass = newPass;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetypePass() {
        return retypePass;
    }

    public void setRetypePass(String retypePass) {
        this.retypePass = retypePass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
