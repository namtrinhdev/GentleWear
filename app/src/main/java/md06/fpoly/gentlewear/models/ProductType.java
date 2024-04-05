package md06.fpoly.gentlewear.models;

import java.io.Serializable;

public class ProductType implements Serializable {
    private String _id, tenLoai;
    private boolean isSelected;

    public ProductType(String productTypeId) {
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
