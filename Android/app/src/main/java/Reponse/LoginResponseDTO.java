package Reponse;

public class LoginResponseDTO {
    private String token;
    private int roleId;
    private int userId;
    private String name;
    private int conversationId;

    public LoginResponseDTO(String token, int roleId, int userId, String name, int conversationId) {
        this.token = token;
        this.roleId = roleId;
        this.userId = userId;
        this.name = name;
        this.conversationId = conversationId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
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
