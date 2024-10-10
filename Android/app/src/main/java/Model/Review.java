package Model;

public class Review {
    private int id;
    private int productId;
    private int userId;
    private int orderId;
    private String imageUrl;
    private String note;
    private float numberOfStars;
    private String sizeProduct;
    private int serviceStars;
    private int deliveryStars;
    private int applicationStars;

    public Review() {
    }

    // Constructor
    public Review(String imageUrl, String note, float numberOfStars, int serviceStars) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.orderId = orderId;
        this.imageUrl = imageUrl;
        this.note = note;
        this.numberOfStars = numberOfStars;
        this.sizeProduct = sizeProduct;
        this.serviceStars = serviceStars;
        this.deliveryStars = deliveryStars;
        this.applicationStars = applicationStars;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(float numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public String getSizeProduct() {
        return sizeProduct;
    }

    public void setSizeProduct(String sizeProduct) {
        this.sizeProduct = sizeProduct;
    }

    public int getServiceStars() {
        return serviceStars;
    }

    public void setServiceStars(int serviceStars) {
        this.serviceStars = serviceStars;
    }

    public int getDeliveryStars() {
        return deliveryStars;
    }

    public void setDeliveryStars(int deliveryStars) {
        this.deliveryStars = deliveryStars;
    }

    public int getApplicationStars() {
        return applicationStars;
    }

    public void setApplicationStars(int applicationStars) {
        this.applicationStars = applicationStars;
    }
}

