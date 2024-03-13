package md06.fpoly.gentlewear.models;

import java.util.List;

public class Products {
    private String _id, productName;
    private int price, quantity;
    private List<ProductType> productType;
    private List<Size> size;
    private int quantitySold;

    public Products() {
    }

    public Products(String _id, String productName, int price, int quantity, List<ProductType> productType, List<Size> size, int quantitySold) {
        this._id = _id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productType = productType;
        this.size = size;
        this.quantitySold = quantitySold;
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

    public List<ProductType> getProductType() {
        return productType;
    }

    public void setProductType(List<ProductType> productType) {
        this.productType = productType;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
}
