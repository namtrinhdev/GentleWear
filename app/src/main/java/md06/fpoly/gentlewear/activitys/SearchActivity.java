package md06.fpoly.gentlewear.activitys;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.Next_interface;
import md06.fpoly.gentlewear.apiServices.ProductAPIServices;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.controller.Adapter.SearchAdapter;
import md06.fpoly.gentlewear.models.Products;
import md06.fpoly.gentlewear.models.ResProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    EditText et_search_product;
    ImageView btn_back;
    private ProductAPIServices apiServices;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        et_search_product = findViewById(R.id.ed_search);
        btn_back = findViewById(R.id.btn_back);

        // Khởi tạo Retrofit client
        apiServices = RetrofitClientAPI.getRetrofitInstance().create(ProductAPIServices.class);

        btn_back.setOnClickListener(v -> {
            onBackPressed();
        });

        // Khởi tạo adapter ở đây
        searchAdapter = new SearchAdapter(this, new Next_interface() {
            @Override
            public void onNextPage(Products products) {
                try {
                    // Kiểm tra xem fragment hoặc activity có tồn tại không
                    if (getContext() != null) {
                        // Chuyển sang màn hình chi tiết sản phẩm với sản phẩm đã chọn
                        Intent intent = new Intent(SearchActivity.this, DetailProductsActivity.class);
                        intent.putExtra("product_data", products);
                        startActivity(intent);
                    } else {
                        Log.e(TAG, "Activity is null");
                        Toast.makeText(SearchActivity.this, "Error navigating to detail screen", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error navigating to detail screen: " + e.getMessage());
                    Toast.makeText(SearchActivity.this, "Error navigating to detail screen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClickItem(String id) {

            }
        });

        recyclerView = findViewById(R.id.rv_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(SearchActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Thực hiện tìm kiếm khi người dùng nhấn Enter trên bàn phím
//        Button btnSearch = findViewById(R.id.btn_search);
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Thực hiện tìm kiếm khi nút được nhấn
//                String query = et_search_product.getText().toString().trim();
//                performSearch(query);
//            }
//        });
        et_search_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần thực hiện gì trước khi văn bản thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không cần thực hiện gì khi văn bản thay đổi
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Sau khi văn bản thay đổi, thực hiện tìm kiếm dữ liệu
                String query = s.toString().trim();
                performSearch(query);
            }
        });
        et_search_product.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Thực hiện tìm kiếm khi nhấn Enter
                String query = et_search_product.getText().toString().trim();
                performSearch(query);
                return true;
            }
            return false;
        });
    }

    private void performSearch(String query) {
        Call<ResProduct> call = apiServices.getResultSearch(query, 1,20);
        call.enqueue(new Callback<ResProduct>() {
            @Override
            public void onResponse(Call<ResProduct> call, Response<ResProduct> response) {
                if (response.isSuccessful()) {
                    ResProduct searchResult = response.body();
                    if (searchResult != null && searchResult.getData() != null) {
                        if (searchAdapter instanceof SearchAdapter) {
                            // Hiển thị kết quả tìm kiếm bằng cách cập nhật dữ liệu của Adapter
                            List<Products> searchResults = searchResult.getData();
                            searchAdapter.updateData((ArrayList<Products>)searchResults);


                        }else {
//                            HomeAdapter homeAdapter = new HomeAdapter((ArrayList<Products>)searchResults);
//                             recyclerView.setAdapter(homeAdapter);
                        }
                    } else {
                        Toast.makeText(SearchActivity.this, "Không tìm thấy kết quả phù hợp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SearchActivity.this, "Lỗi khi tìm kiếm sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResProduct> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Lỗi khi tìm kiếm sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

