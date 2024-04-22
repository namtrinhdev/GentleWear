package md06.fpoly.gentlewear.models;

import java.util.List;

public class Favorites {
    private String _id;
    private Users user;
    private List<Products> products;

    public Favorites() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
