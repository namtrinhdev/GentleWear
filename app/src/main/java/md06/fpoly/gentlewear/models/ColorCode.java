package md06.fpoly.gentlewear.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class ColorCode implements Serializable {
    private String _id, colorCode, nameColor;

    public ColorCode() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ColorCode colorCode = (ColorCode) obj;
        return Objects.equals(_id, colorCode._id) &&
                Objects.equals(this.colorCode, colorCode.colorCode) &&
                Objects.equals(this.nameColor, colorCode.nameColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, colorCode, nameColor);
    }
}
