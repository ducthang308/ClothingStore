package Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

public class Message {
    @SerializedName("id")
    private int id;

    @SerializedName("senderId")
    private int senderId;

    @SerializedName("receiver_id")
    private int receiverId;

    @SerializedName("content")
    private String content;

    @SerializedName("create_at")
    private Date createAt;

    private int conversationId;

    // Constructor
    public Message(int id, int senderId, int receiverId, String content, Date createAt, int conversationId) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.createAt = createAt;
        this.conversationId = conversationId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public Message(int senderId, int receiverId, String content, Date createAt) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.createAt = createAt;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setCreateAt(Date sentAt) {
        this.createAt = createAt;
    }
}

