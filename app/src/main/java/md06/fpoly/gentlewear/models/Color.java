package md06.fpoly.gentlewear.models;

import java.io.Serializable;
import java.util.Objects;

public class Color implements Serializable {
    private String _id;
    private ColorCode colorCode;
    private String image;
    private int quantity;

    public Color() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ColorCode getColorCode() {
        return colorCode;
    }

    public void setColorCode(ColorCode colorCode) {
        this.colorCode = colorCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Color color = (Color) obj;
        return quantity == color.quantity &&
                Objects.equals(_id, color._id) &&
                Objects.equals(colorCode, color.colorCode) &&
                Objects.equals(image, color.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, colorCode, image, quantity);
    }
}
