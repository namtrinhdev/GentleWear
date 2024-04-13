package md06.fpoly.gentlewear.models;

import java.io.Serializable;
import java.util.List;

public class ThanhToan implements Serializable {
    private String _id;
    private List<Cart> cart;
    private Users user;
    private int tongTien;
    private String thoiGian;
    private int trangThai;
    private List<StatusUpdate> statusUpdates;
    private int payOptions;
    private List<Products> productList;

    public ThanhToan() {
    }

    public ThanhToan(String _id, List<Cart> cart, Users user, int tongTien, String thoiGian, int trangThai, List<StatusUpdate> statusUpdates, int payOptions) {
        this._id = _id;
        this.cart = cart;
        this.user = user;
        this.tongTien = tongTien;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
        this.statusUpdates = statusUpdates;
        this.payOptions = payOptions;
    }
    public List<Products> getProductList() {
        return productList;
    }

    public void setProductList(List<Products> productList) {
        this.productList = productList;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
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

    public int getPayOptions() {
        return payOptions;
    }

    public void setPayOptions(int payOptions) {
        this.payOptions = payOptions;
    }
}
