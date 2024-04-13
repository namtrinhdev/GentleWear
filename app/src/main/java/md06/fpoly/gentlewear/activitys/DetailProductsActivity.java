package md06.fpoly.gentlewear.activitys;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.controller.Adapter.CartAdapter;
import md06.fpoly.gentlewear.controller.Adapter.Color_Adapter;
import md06.fpoly.gentlewear.models.Cart;
import md06.fpoly.gentlewear.models.Cart2;
import md06.fpoly.gentlewear.models.Colors;
import md06.fpoly.gentlewear.models.ColorCodes;
import md06.fpoly.gentlewear.models.ProductType;
import md06.fpoly.gentlewear.models.Products;
import md06.fpoly.gentlewear.models.Sizes;
import md06.fpoly.gentlewear.models.SizeCodes;

public class DetailProductsActivity extends AppCompatActivity {
    private Products products;
    private RecyclerView colorsRecyclerView;
    private TextView tv_mota, tv_namepro, tv_quantity, tv_price, tv_S, tv_M, tv_L, tv_XL, tv_XXL;
    private static final String M = "M", L = "L", S = "S", XL = "XL", XXL = "XXL";
    private ImageView img_product, img_backpress, img_add, img_remove;
    private FrameLayout btn_themGH;
    private int count = 1, status = 0;
    private String size;
    private Color_Adapter colorAdapter;
    private List<Cart> list = Cart2.getInstance().getCart();
    private Products sp = new Products();
    private String selectedSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_products);
        init();

        Intent intent = getIntent();
        products = (Products) intent.getSerializableExtra("product_data");


        TextView sizeS = findViewById(R.id.size_s);
        TextView sizeM = findViewById(R.id.size_m);
        TextView sizeL = findViewById(R.id.size_l);
        TextView sizeXL = findViewById(R.id.size_xl);
        TextView sizeXXL = findViewById(R.id.size_xxl);

        CartAdapter cartAdapter = new CartAdapter();

        // Khởi tạo RecyclerView và adapter
        List<Colors> colorsList = new ArrayList<>();
        colorsRecyclerView = findViewById(R.id.rc_mau);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        colorsRecyclerView.setLayoutManager(layoutManager);
        colorAdapter = new Color_Adapter(colorsList, this);
        colorsRecyclerView.setAdapter(colorAdapter);


        for (Sizes size : products.getSize()) {
            for (Colors color : size.getColor()) {
                colorsList.add(color); // Thêm đối tượng Colors vào danh sách
            }
        }

        colorAdapter.setdata(colorsList);


        View.OnClickListener sizeListener = v -> {
            // Initialize selectedSize variable
            int id = v.getId();
            if (id == R.id.size_s) {
                selectedSize = S;
            } else if (id == R.id.size_m) {
                selectedSize = M;
            } else if (id == R.id.size_l) {
                selectedSize = L;
            } else if (id == R.id.size_xl) {
                selectedSize = XL;
            } else if (id == R.id.size_xxl) {
                selectedSize = XXL;
            }
            setSizeExist(selectedSize); // Update UI based on the selected size
        };

// Remember to attach this listener to your size TextViews
        sizeS.setOnClickListener(sizeListener);
        sizeM.setOnClickListener(sizeListener);
        sizeL.setOnClickListener(sizeListener);
        sizeXL.setOnClickListener(sizeListener);
        sizeXXL.setOnClickListener(sizeListener);
        tv_namepro.setText(products.getProductName());
        tv_mota.setText(products.getMota());

        Glide.with(this).load(products.getImage()).apply(RequestOptions.centerCropTransform()).into(img_product);
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

        btn_themGH.setOnClickListener(view -> {
            if (count != 0) {
                Cart cart = new Cart(products, count, selectedSize);
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

    private void mProducts (){
        Products products1 = new Products();
        ColorCodes colorCode = new ColorCodes();
        ProductType productType = new ProductType();
        Sizes size1 = new Sizes();
        SizeCodes sizeCode = new SizeCodes();
        size1.get_id();
        size1.getSizeCode();
    }

// Set other properties as needed

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