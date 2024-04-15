package md06.fpoly.gentlewear.models;


import java.io.Serializable;
import java.util.List;

public class Size implements Serializable {
    private String _id;
    private SizeCode sizeCode;
    private List<Color> color;
    private int quantity;

    public Size() {
    }

    public Size(String sizeId) {
    }

    public Size(String _id, SizeCode sizeCode, List<Color> color, int quantity) {
        this._id = _id;
        this.sizeCode = sizeCode;
        this.color = color;
        this.quantity = quantity;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public SizeCode getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(SizeCode sizeCode) {
        this.sizeCode = sizeCode;
    }

    public List<Color> getColor() {
        return color;
    }

    public void setColor(List<Color> color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
