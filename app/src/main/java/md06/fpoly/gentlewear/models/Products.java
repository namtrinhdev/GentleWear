package md06.fpoly.gentlewear.models;

public class Products {
    int id;
    String tenSP;
    int price;
    int quantity;
    int idProductType;
    int sizeID;
    int quantitySlod;

    public Products() {
    }

    public Products(int id, String tenSP, int price, int quantity, int idProductType, int sizeID, int quantitySlod) {
        this.id = id;
        this.tenSP = tenSP;
        this.price = price;
        this.quantity = quantity;
        this.idProductType = idProductType;
        this.sizeID = sizeID;
        this.quantitySlod = quantitySlod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
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

    public int getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(int idProductType) {
        this.idProductType = idProductType;
    }

    public int getSizeID() {
        return sizeID;
    }

    public void setSizeID(int sizeID) {
        this.sizeID = sizeID;
    }

    public int getQuantitySlod() {
        return quantitySlod;
    }

    public void setQuantitySlod(int quantitySlod) {
        this.quantitySlod = quantitySlod;
    }
}
