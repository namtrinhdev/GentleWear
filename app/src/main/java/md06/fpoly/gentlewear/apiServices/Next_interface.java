package md06.fpoly.gentlewear.apiServices;

import md06.fpoly.gentlewear.models.Products;

public interface Next_interface {
    void onNextPage(Products products);
    void onClickItem(String id);
}
