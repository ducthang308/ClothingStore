package Model;

public class Discount {
    private int id;
    private float discount;
    private String mota;

    public Discount(int id, float discount, String mota) {
        this.id = id;
        this.discount = discount;
        this.mota = mota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
