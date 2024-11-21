package DTO;

import com.google.gson.annotations.SerializedName;

public class CategoriesDTO {
    @SerializedName("id")
    private long id;

    @SerializedName("category_name")  // Đảm bảo khớp với backend
    private String categoryName;

    // Constructor chỉ có tham số name (dùng cho tạo mới)
    public CategoriesDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    // Constructor có cả id và name (dùng cho cập nhật nếu cần)
    public CategoriesDTO(long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    // Default constructor
    public CategoriesDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
