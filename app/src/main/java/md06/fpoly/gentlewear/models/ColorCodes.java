package md06.fpoly.gentlewear.models;

import java.io.Serializable;

public class ColorCodes implements Serializable {
    private String _id, nameColor,image;

    public ColorCodes() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }



    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }
}
