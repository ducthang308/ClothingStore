package Model;

import android.os.Build;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Carts {
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("created_at")
    private LocalDateTime createdAt;

    @SerializedName("updated_at")
    private LocalDateTime updatedAt;

    public Carts(int userId) {
        this.userId = userId;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            this.createdAt = LocalDateTime.now();
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            this.updatedAt = LocalDateTime.now();
//        }
    }

//    public Carts(int userId) {
//        this.userId = userId;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
