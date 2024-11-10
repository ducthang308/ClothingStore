package DTO;

import com.google.gson.annotations.SerializedName;

public class CategoriesDTO {
    @SerializedName("id")
    private long id;

    @SerializedName("category_name") // Đổi "caterogy_name" thành "category_name" để khớp với backend
    private String name;

    public CategoriesDTO() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Constructor thêm name
    public CategoriesDTO( String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
