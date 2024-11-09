package Model;

public class Discount {
    private int id;
    private float percent;
    private String note;

    public Discount(int id, float percent, String note) {
        this.id = id;
        this.percent = percent;
        this.note = note;
    }

    public Discount(float percent, String note) {
        this.percent = percent;
        this.note = note;
    }

    public Discount() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPercent() { // Đổi tên phương thức cho nhất quán
        return percent;
    }

    public void setPercent(float percent) { // Đổi tên phương thức cho nhất quán
        this.percent = percent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
