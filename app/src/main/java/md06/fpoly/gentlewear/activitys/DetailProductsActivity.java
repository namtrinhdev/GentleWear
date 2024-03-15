package md06.fpoly.gentlewear.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.models.Cart;
import md06.fpoly.gentlewear.models.Cart2;
import md06.fpoly.gentlewear.models.Products;

public class DetailProductsActivity extends AppCompatActivity {
    private Products products;
    private TextView tv_mota, tv_namepro, tv_quantity, tv_price;

    private ImageView img_product, img_backpress,img_add,img_remove;
    private FrameLayout btn_themGH;
    private int count = 1;
    private List<Cart> list = Cart2.getInstance().getCart();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_products);

        tv_mota = findViewById(R.id.description_text);
        tv_namepro = findViewById(R.id.product_name_text);
        tv_quantity = findViewById(R.id.tv_number_food_detail);
        tv_price = findViewById(R.id.product_price_text);
        img_add = findViewById(R.id.img_add);
        img_remove = findViewById(R.id.img_remove);
        img_product = findViewById(R.id.anh_sp);
        img_backpress = findViewById(R.id.btnback);
        btn_themGH = findViewById(R.id.btn_themGH);

        Intent intent = getIntent();
        products = (Products) intent.getSerializableExtra("product_data");

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
        if (getQuantity() == products.getQuantity()){
            count = 0;
            tv_quantity.setText(String.valueOf(count));
            tv_price.setText(count + " đ");
        }
        //
        img_add.setOnClickListener(view -> {
            if (products != null && products.get_id() != null) {
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

    private int getQuantity(){
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

    private int getIndex(){
        for (int i = 0; i<list.size(); i++){
            if (list.get(i).getProducts().get_id().equals(products.get_id())) {
                return i;
            }
        }
        return -1;
    }
}