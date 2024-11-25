package Reponse;

public class LoginResponseDTO {
    private String token;
    private int roleId;
    private int userId;
    private String name;

    public LoginResponseDTO(String token, int roleId, int userId, String name) {
        this.token = token;
        this.roleId = roleId;
        this.userId = userId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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
