package md06.fpoly.gentlewear.models;

import java.io.Serializable;

public class SizeCode implements Serializable {
    private String _id, sizeCode;

    public SizeCode() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }
}
