package md06.fpoly.gentlewear.models;

import java.io.Serializable;

public class Colors implements Serializable {
    private String _id;
    private ColorCodes colorCode;
    private int quantity;

    public Colors() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ColorCodes getColorCode() {
        return colorCode;
    }

    public void setColorCode(ColorCodes colorCode) {
        this.colorCode = colorCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
