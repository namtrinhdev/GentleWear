package md06.fpoly.gentlewear.models;

import java.io.Serializable;
import java.util.List;

public class Products implements Serializable {
    private String _id, productName;
    private int price, quantity,soTien;
    private ProductType productType;
    private List<Sizes> size;
    private int quantitySold;
    private String image, mota;


    public Products(String _id, String productName, int price, int quantity, int soTien, ProductType productType, List<Sizes> size, int quantitySold, String image, String mota) {
        this._id = _id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.soTien = soTien;
        this.productType = productType;
        this.size = size;
        this.quantitySold = quantitySold;
        this.image = image;
        this.mota = mota;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public Products() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public List<Sizes> getSize() {
        return size;
    }

    public void setSize(List<Sizes> size) {
        this.size = size;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
}
