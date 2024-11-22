package Model;

public class CartItem {
    private String name;
    private String size;
    private float price;
    private int imageUrl;
    private boolean isSelected;

    public CartItem(String name, String size, float price, int imageUrl, boolean isSelected) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isSelected = isSelected;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
