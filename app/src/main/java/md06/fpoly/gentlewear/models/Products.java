package md06.fpoly.gentlewear.models;

import java.io.Serializable;
import java.util.List;

public class Products implements Serializable {
    private String _id, productName;
    private int price, quantity;
    private ProductType productType;
    private List<Sizes> size;
    private int quantitySold;
    private String mota;


    public Products(String _id, String productName, int price, int quantity, ProductType productType, List<Sizes> size, int quantitySold, String mota) {
        this._id = _id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productType = productType;
        this.size = size;
        this.quantitySold = quantitySold;
        this.mota = mota;
    }

    public Products() {

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
    public boolean compareProducts(Products product1, Products product2) {
        // So sánh _id
        if (!product1.get_id().equals(product2.get_id())) {
            return false;
        }

        // So sánh price
        if (product1.getPrice() != product2.getPrice()) {
            return false;
        }

        // So sánh size
        if (!product1.getSize().get(0).get_id().equals(product2.getSize().get(0).get_id())) {
            return false;
        }
        if (!product1.getSize().get(0).getColor().get(0).get_id().equals(product2.getSize().get(0).getColor().get(0).get_id())) {
            return false;
        }

        // Trả về true nếu tất cả các trường đều trùng khớp
        return true;
    }
}
