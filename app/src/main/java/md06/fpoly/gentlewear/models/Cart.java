package md06.fpoly.gentlewear.models;

import java.io.Serializable;

public class Cart implements Serializable {
    private Products products;
    private int soLuong;

    public Cart() {
    }

    public Cart(Products products, int soLuong) {
        this.products = products;
        this.soLuong = soLuong;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public int getTotalPrice(){
        return soLuong * products.getPrice();
    }
}
