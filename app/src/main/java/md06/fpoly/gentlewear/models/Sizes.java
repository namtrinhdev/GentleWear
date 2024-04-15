package md06.fpoly.gentlewear.models;


import java.io.Serializable;
import java.util.List;

public class Sizes implements Serializable {
    private String _id;
    private SizeCodes sizeCode;
    private List<Colors> color;
    private int quantity;

    public Sizes() {
    }

    public Sizes(String _id, SizeCodes sizeCode, List<Colors> color, int quantity) {
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

    public SizeCodes getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(SizeCodes sizeCode) {
        this.sizeCode = sizeCode;
    }

    public List<Colors> getColor() {
        return color;
    }

    public void setColor(List<Colors> color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
