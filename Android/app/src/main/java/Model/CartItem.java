package Model;

public class CartItem {
    private String name;
    private String size;
    private String price;
    private String imageUrl;

    public CartItem(String name, String size, String price, String imageUrl) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
