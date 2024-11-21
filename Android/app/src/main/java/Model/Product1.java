package Model;

public class Product1 {
    private int id;
    private String productName;
    private int categoryId;
    private String color;
    private String size;
    private float price;
    private float discount;
    private int soluong;

    public Product1(int id, String productName, int categoryId, String color, String size, float price, float discount, int soluong) {
        this.id = id;
        this.productName = productName;
        this.categoryId = categoryId;
        this.color = color;
        this.size = size;
        this.price = price;
        this.discount = discount;
        this.soluong = soluong;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}