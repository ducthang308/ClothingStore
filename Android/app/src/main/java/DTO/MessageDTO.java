package DTO;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MessageDTO {
    private String content;
    private int receiverId;
    @SerializedName("senderId")
    private int senderId;
    private Date createAt;
    private String senderFullName;
    private int conversationId;

    public MessageDTO(String content, int receiverId, int senderId, Date createAt, String senderFullName, int conversationId) {
        this.content = content;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.createAt = createAt;
        this.senderFullName = senderFullName;
        this.conversationId = conversationId;
    }

    public MessageDTO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getSenderId() {return senderId;}

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getSenderFullName() {
        return senderFullName;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }

    public int getConversationId() {return conversationId;}

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }
}
