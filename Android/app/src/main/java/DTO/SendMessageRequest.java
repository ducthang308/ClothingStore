package DTO;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SendMessageRequest {
    @SerializedName("senderId")
    private int senderId;

    @SerializedName("receiverId")
    private int receiverId;

    private String content;

    @SerializedName("createAt")
    private Date createAt;

    public SendMessageRequest(int senderId, int receiverId, String content, Date createAt) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.createAt = createAt;
    }

    public SendMessageRequest(int senderId, int receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}

