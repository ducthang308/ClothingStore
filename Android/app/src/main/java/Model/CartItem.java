package Model;

public class CartItem {
    private String name;
    private String size;
    private String price;
    private int imageUrl;  // Thay đổi từ String sang int

    public CartItem(String name, String size, String price, int imageUrl) { // Chuyển imageUrl sang kiểu int
        this.name = name;
        this.size = size;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public CartItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageUrl() {  // Trả về int thay vì String
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {  // Chuyển đổi setter thành int
        this.imageUrl = imageUrl;
    }
}
