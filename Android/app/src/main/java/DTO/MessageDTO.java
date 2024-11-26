package DTO;

import java.util.Date;

public class MessageDTO {
    private String content;
    private int receiverId;
    private Date createAt;
    private String senderFullName;

    public MessageDTO(String content, int receiverId, String senderFullName, Date createAt) {
        this.content = content;
        this.receiverId = receiverId;
        this.senderFullName = senderFullName;
        this.createAt = createAt;
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

    public String getSenderFullName() {
        return senderFullName;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
