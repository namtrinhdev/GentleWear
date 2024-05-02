package md06.fpoly.gentlewear.models;

import java.io.Serializable;
import java.util.Objects;

public class Colors implements Serializable {
    private String _id;
    private ColorCodes colorCode;
    private int quantity;
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Colors color = (Colors) obj;
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
