package md06.fpoly.gentlewear.models;

import java.io.Serializable;
import java.util.List;

public class NapTien implements Serializable {
    private String _id;
    private Users userModel;
    private int soTienNap;
    private String thoiGian;
    private int trangThai;
    private List<StatusUpdate> statusUpdates;
    private String anhGiaoDich;

    public NapTien(String _id, Users userModel, int soTienNap, String thoiGian, int trangThai, List<StatusUpdate> statusUpdates, String anhGiaoDich) {
        this._id = _id;
        this.userModel = userModel;
        this.soTienNap = soTienNap;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
        this.statusUpdates = statusUpdates;
        this.anhGiaoDich = anhGiaoDich;
    }

    public NapTien() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getSoTienNap() {
        return soTienNap;
    }

    public void setSoTienNap(int soTienNap) {
        this.soTienNap = soTienNap;
    }

    public Users getUserModel() {
        return userModel;
    }

    public void setUserModel(Users userModel) {
        this.userModel = userModel;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public List<StatusUpdate> getStatusUpdates() {
        return statusUpdates;
    }

    public void setStatusUpdates(List<StatusUpdate> statusUpdates) {
        this.statusUpdates = statusUpdates;
    }

    public String getAnhGiaoDich() {
        return anhGiaoDich;
    }

    public void setAnhGiaoDich(String anhGiaoDich) {
        this.anhGiaoDich = anhGiaoDich;
    }
}
