package md06.fpoly.gentlewear.models;

import java.io.Serializable;

public class Cart implements Serializable {
    private Products products;
    private int soLuong;
    private String size;
    public Cart() {
    }

    public Cart(Products products, int soLuong, String size) {
        this.products = products;
        this.soLuong = soLuong;
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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
