package Reponse;

public class LoginResponseDTO {
    private String token;
    private int roleId;

    public LoginResponseDTO(String token, int roleId) {
        this.token = token;
        this.roleId = roleId;
    }

    // Getters and setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
