package md06.fpoly.gentlewear.models;

public class ResProduct {
    private int page, pageSize, totalItems;
    private Products data;

    public ResProduct() {
    }

    public ResProduct(int page, int pageSize, int totalItems, Products data) {
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

    public Products getData() {
        return data;
    }

    public void setData(Products data) {
        this.data = data;
    }
}
