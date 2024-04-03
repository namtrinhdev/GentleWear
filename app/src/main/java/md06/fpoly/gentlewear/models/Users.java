package md06.fpoly.gentlewear.models;

import java.io.Serializable;

public class Users implements Serializable {
    private String _id,fullname,email,sdt,passwd,diaChi;
    private int status;
    private boolean isLocked;
    private String avatar;

    public Users() {
    }

    public Users(String _id, String fullname, String email, String sdt, String passwd, String diaChi,int status,boolean isLocked, String avatar) {
        this._id = _id;
        this.fullname = fullname;
        this.email = email;
        this.sdt = sdt;
        this.passwd = passwd;
        this.diaChi = diaChi;
        this.status = status;
        this.isLocked = isLocked;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
