package md06.fpoly.gentlewear.activitys;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.controller.Adapter.Adapter_Color_DetailProd;
import md06.fpoly.gentlewear.interfaces.DetailProd_Color_Interface;
import md06.fpoly.gentlewear.models.Cart;
import md06.fpoly.gentlewear.models.Cart2;
import md06.fpoly.gentlewear.models.Color;
import md06.fpoly.gentlewear.models.ColorCode;
import md06.fpoly.gentlewear.models.Products;
import md06.fpoly.gentlewear.models.Size;
import md06.fpoly.gentlewear.models.SizeCode;

public class DetailProductsActivity extends AppCompatActivity {
    private Products products;
    private TextView tv_mota, tv_namepro, tv_quantity, tv_price, tv_S, tv_M, tv_L, tv_XL, tv_XXL;
    private static final String M = "M", L = "L", S = "S", XL = "XL", XXL = "XXL";
    private ImageView img_product, img_backpress, img_add, img_remove;
    private FrameLayout btn_themGH;
    private int count = 1, status = 0;
    private String size;
    private List<Cart> list = Cart2.getInstance().getCart();
    private Products sp = new Products();
    private Size s;
    private SizeCode sc =new SizeCode();
    private Color color;
    private ColorCode cCode = new ColorCode();
    private Adapter_Color_DetailProd adapter;
    private RecyclerView recyclerViewColor;
    private List<Color> colorList = new ArrayList<>();
    private List<ColorCode> codeList = new ArrayList<>();
    private int sizeCount = 0;
    private int colorCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_products);
        init();

        Intent intent = getIntent();
        products = (Products) intent.getSerializableExtra("product_data");

        tv_namepro.setText(products.getProductName());
        tv_mota.setText(products.getMota());

        tv_quantity.setText(String.valueOf(count));
        tv_price.setText(products.getPrice() + " đ");
        if (products.getQuantity() == 0) {
            count = 0;
            tv_quantity.setText(String.valueOf(count));
            tv_price.setText(count + " đ");
        }
        if (getQuantity() == products.getQuantity()) {
            count = 0;
            tv_quantity.setText(String.valueOf(count));
            tv_price.setText(count + " đ");
        }
        //
        img_add.setOnClickListener(view -> {
            if (list.size() != 0) {
                int index = -1;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getProducts().get_id().equals(products.get_id())) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    if (list.get(index).getSoLuong() <= products.getQuantity()) {
                        if (count < products.getQuantity() - list.get(index).getSoLuong() && count < 15) {
                            count++;
                            tv_quantity.setText(String.valueOf(count));
                            tv_price.setText(products.getPrice() * count + " đ");
                        }
                    }
                } else {
                    if (count < products.getQuantity() && count < 15) {
                        count++;
                        tv_quantity.setText(String.valueOf(count));
                        tv_price.setText(products.getPrice() * count + " đ");
                    }
                }

            } else {
                //list null
                if (count < products.getQuantity() && count < 15) {
                    count++;
                    tv_quantity.setText(String.valueOf(count));
                    tv_price.setText(products.getPrice() * count + " đ");
                }
            }

        });

        img_remove.setOnClickListener(view -> {
            if (count > 1) {
                count--;
                tv_quantity.setText(String.valueOf(count));
                tv_price.setText(products.getPrice() * count + " đ");
            }
        });
        img_backpress.setOnClickListener(view -> {
            onBackPressed();
        });

        //lay ds color
        setDataListColor();
        //load ds color
        loadColor(codeList);

        //set size
        List<String> sList = new ArrayList<>();
        List<SizeCode> sCodeList = new ArrayList<>();
        for (int i = 0; i<sizeCount; i++){
            sList.add(products.getSize().get(i).getSizeCode().getSizeCode());
            setSizeExist(products.getSize().get(i).getSizeCode().getSizeCode());
            sCodeList.add(products.getSize().get(i).getSizeCode());
            if (products.getSize().get(i).getQuantity()<1){
              SizeExistButNoQty(products.getSize().get(i).getSizeCode().getSizeCode());
            }
        }
        for (String a : sList){
            setSizeExist(a);
        }

        //check click size
        tv_S.setOnClickListener(v -> {
            Toast.makeText(this, "S", Toast.LENGTH_SHORT).show();
        });
        tv_M.setOnClickListener(v -> {
            Toast.makeText(this, "M", Toast.LENGTH_SHORT).show();
        });
        tv_L.setOnClickListener(v -> {
            Toast.makeText(this, "L", Toast.LENGTH_SHORT).show();
        });
        tv_XL.setOnClickListener(v -> {
            Toast.makeText(this, "XL", Toast.LENGTH_SHORT).show();
        });
        tv_XXL.setOnClickListener(v -> {
            Toast.makeText(this, "XXL", Toast.LENGTH_SHORT).show();
        });



        //set product
        setProduct();

        btn_themGH.setOnClickListener(view -> {
            if (count != 0) {
                Cart cart = new Cart(products, count);
                if (list.size() == 0) {
                    list.add(cart);
                    Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    if (products.getQuantity() == count) {
                        count = 0;
                        tv_quantity.setText(String.valueOf(count));
                        tv_price.setText(products.getPrice() * count + " đ");
                    } else {
                        count = 1;
                        tv_quantity.setText(String.valueOf(count));
                        tv_price.setText(products.getPrice() * count + " đ");
                    }

                } else {
                    int index = -1;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getProducts().get_id().equals(cart.getProducts().get_id())) {
                            index = i;
                            break;
                        }
                    }
                    if (index != -1) {
                        int sum = list.get(index).getSoLuong() + count;
                        list.get(index).setSoLuong(sum);
                        if (products.getQuantity() > sum) {
                            count = 1;
                            tv_quantity.setText(String.valueOf(count));
                            tv_price.setText(products.getPrice() * count + " đ");
                        } else {
                            count = 0;
                            tv_quantity.setText(String.valueOf(count));
                            tv_price.setText(count * products.getPrice() + " đ");
                        }
                    } else {
                        Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        list.add(cart);
                        if (products.getQuantity() == count) {
                            count = 0;
                            tv_quantity.setText(String.valueOf(count));
                            tv_price.setText(products.getPrice() * count + " đ");
                        } else {
                            count = 1;
                            tv_quantity.setText(String.valueOf(count));
                            tv_price.setText(products.getPrice() * count + " đ");
                        }
                    }
                }
            }
        });

    }
    private void setDataListColor() {
        sizeCount = products.getSize().size();
        for (int i =0; i<sizeCount; i++){
            for (int j= 0; j<products.getSize().get(i).getColor().size();j++){
                if (!colorList.contains(products.getSize().get(i).getColor().get(j))){
                    colorList.add(products.getSize().get(i).getColor().get(j));
                    codeList.add(products.getSize().get(i).getColor().get(j).getColorCode());
                }
            }
        }
    }

    private void loadColor(List<ColorCode> codeList) {
        adapter = new Adapter_Color_DetailProd(codeList, colorCode -> {
            cCode = colorCode;
        });
        recyclerViewColor.setLayoutManager(new GridLayoutManager(this,5));
        recyclerViewColor.setAdapter(adapter);
    }

    private void setProduct() {
        sp.set_id(products.get_id());
        sp.setProductName(products.getProductName());
        sp.setPrice(products.getPrice());
        sp.setQuantity(products.getQuantity());
        sp.setProductType(products.getProductType());
        sp.setQuantitySold(products.getQuantitySold());
        sp.setMota(products.getMota());
    }
    private void setSize(String _id, SizeCode sizeCode, List<Color> color, int quantity){
        s= new Size(_id, sizeCode, color, quantity);
    }
    private void setSizeCode(SizeCode sizeCode){
        sc = sizeCode;
    }

    private int getQuantity() {
        if (list.size() != 0) {
            if (getIndex() != -1) {
                return list.get(getIndex()).getSoLuong();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private int getIndex() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProducts().get_id().equals(products.get_id())) {
                return i;
            }
        }
        return -1;
    }

    private void init() {
        tv_mota = findViewById(R.id.description_text);
        tv_namepro = findViewById(R.id.product_name_text);
        tv_quantity = findViewById(R.id.tv_number_food_detail);
        tv_price = findViewById(R.id.product_price_text);
        img_add = findViewById(R.id.img_add);
        img_remove = findViewById(R.id.img_remove);
        img_product = findViewById(R.id.anh_sp);
        img_backpress = findViewById(R.id.btnback);
        btn_themGH = findViewById(R.id.btn_themGH);
        tv_S = findViewById(R.id.size_s);
        tv_M = findViewById(R.id.size_m);
        tv_L = findViewById(R.id.size_l);
        tv_XL = findViewById(R.id.size_xl);
        tv_XXL = findViewById(R.id.size_xxl);
        recyclerViewColor = findViewById(R.id.ryc_color_detail);
    }

    private void setBackgroundPick(TextView textView) {
        // Tạo một đối tượng GradientDrawable để định nghĩa hình dạng nền
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE); // Hình dạng chữ nhật
        gradientDrawable.setColor(getResources().getColor(R.color.bg_btn)); // Màu nền
        gradientDrawable.setCornerRadius(30); // Độ cong góc
        gradientDrawable.setStroke(2, getResources().getColor(R.color.white)); // Viền
        textView.setTextColor(getResources().getColor(R.color.white));

        // Thiết lập hình dạng nền cho TextView
        textView.setBackground(gradientDrawable);
    }

    private void setBackgroundDefault(TextView textView) {
        // Tạo một đối tượng GradientDrawable để định nghĩa hình dạng nền
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE); // Hình dạng chữ nhật
        gradientDrawable.setColor(getResources().getColor(R.color.bg_tv_size1)); // Màu nền
        gradientDrawable.setCornerRadius(30); // Độ cong góc
        gradientDrawable.setStroke(2, getResources().getColor(R.color.white)); // Viền
        textView.setTextColor(getResources().getColor(R.color.black));

        // Thiết lập hình dạng nền cho TextView
        textView.setBackground(gradientDrawable);
    }

    private void setBackgroundOff(TextView textView) {
        // Tạo một đối tượng GradientDrawable để định nghĩa hình dạng nền
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE); // Hình dạng chữ nhật
        gradientDrawable.setColor(getResources().getColor(R.color.bg_tv_size2)); // Màu nền
        gradientDrawable.setCornerRadius(30); // Độ cong góc
        gradientDrawable.setStroke(2, getResources().getColor(R.color.white)); // Viền

        // Thiết lập hình dạng nền cho TextView
        textView.setBackground(gradientDrawable);
        textView.setTextColor(getResources().getColor(R.color.text_1));
    }
    private void SizeExistButNoQty(String a) {
        if (a.equals(S)) {
            tv_S.setClickable(false);
            setBackgroundOff(tv_S);
        } else if (a.equals(M)){
            setBackgroundOff(tv_M);
            tv_M.setClickable(false);
        } else if (a.equals(L)){
            setBackgroundOff(tv_L);
            tv_L.setClickable(false);
        } else if (a.equals(XL)){
            setBackgroundOff(tv_XL);
            tv_XL.setClickable(false);
        } else if (a.equals(XXL)){
            setBackgroundOff(tv_XXL);
            tv_XXL.setClickable(false);
        }
    }
    private void setSizeExist(String a) {
        if (a.equals(S)) {
            setBackgroundDefault(tv_S);
        } else {
            tv_S.setClickable(false);
            setBackgroundOff(tv_S);
        }
        if (a.equals(M)) {
            setBackgroundDefault(tv_M);
        } else {
            setBackgroundOff(tv_M);
            tv_S.setClickable(false);
        }
        if (a.equals(L)) {
            setBackgroundDefault(tv_L);
        } else {
            setBackgroundOff(tv_L);
            tv_S.setClickable(false);
        }
        if (a.equals(XL)) {
            setBackgroundDefault(tv_XL);
        } else {
            setBackgroundOff(tv_XL);
            tv_S.setClickable(false);
        }
        if (a.equals(XXL)) {
            setBackgroundDefault(tv_XXL);
        } else {
            setBackgroundOff(tv_XXL);
            tv_S.setClickable(false);
        }
    }
}