package md06.fpoly.gentlewear.models;
import java.io.Serializable;
import java.util.ArrayList;

public class ResProduct implements Serializable {
    private int page, pageSize, totalItems;
    private ArrayList<Products> data;
    private int total;


    public ResProduct() {
    }

    public ResProduct(int page, int pageSize, int totalItems, ArrayList<Products> data) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public ArrayList<Products> getData() {
        return data;
    }

    public void setData(ArrayList<Products> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
