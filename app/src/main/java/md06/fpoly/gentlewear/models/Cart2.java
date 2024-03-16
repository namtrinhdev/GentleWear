package md06.fpoly.gentlewear.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Cart2 {
    public static Cart2 instance;
    private List<Cart> cart = new ArrayList<>();

    public static synchronized Cart2 getInstance(){
        if (instance == null) {
            instance = new Cart2();
        }
        return instance;
    }
    public Cart2() {
    }

    public Cart2(List<Cart> cart) {
        this.cart = cart;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }
    public int getTotalPrice() {
        int totalPrice = 0;
        for (Cart c : cart) {
            totalPrice += c.getTotalPrice();
        }
        return totalPrice;
    }
    public void clear(){
        if (cart != null){
            cart.clear();
            Log.i("TAG", "clear: true");
        }
    }
}
