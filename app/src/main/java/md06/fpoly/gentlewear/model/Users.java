package md06.fpoly.gentlewear.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Users implements Serializable {
    private String _id,fullname,email,sdt,passwd,diaChi;
    private String avatar;

    public Users() {
    }

    public Users(String _id, String fullname, String email, String sdt, String passwd, String diaChi, String avatar) {
        this._id = _id;
        this.fullname = fullname;
        this.email = email;
        this.sdt = sdt;
        this.passwd = passwd;
        this.diaChi = diaChi;
        this.avatar = avatar;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
