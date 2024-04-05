package md06.fpoly.gentlewear.controller.Adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.FilterListener;
import md06.fpoly.gentlewear.apiServices.ProductAPIServices;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.models.ProductType;
import md06.fpoly.gentlewear.models.Products;
import md06.fpoly.gentlewear.models.ResProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BottomSheetFilterFragment extends BottomSheetDialogFragment {
    private static final String TAG = "FrgDia";

    private RecyclerView recyclerViewProductType;
    private ProductTypeAdapter productTypeAdapter;
    private Button buttonApply, buttonCancel;
    private ProductAPIServices productAPIServices;
    private OnProductTypeSelectedListener listener;

    private FilterListener filterListener;

    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
    }
    public interface OnProductTypeSelectedListener {
        void onProductTypeSelected(String productType);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);

        recyclerViewProductType = rootView.findViewById(R.id.recyclerViewProductType);
        buttonApply = rootView.findViewById(R.id.buttonApply);
        buttonCancel = rootView.findViewById(R.id.buttonCancel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewProductType.setLayoutManager(layoutManager);

        productTypeAdapter = new ProductTypeAdapter(getContext(), new ArrayList<>());
        recyclerViewProductType.setAdapter(productTypeAdapter);

        Retrofit retrofit = RetrofitClientAPI.getRetrofitInstance();
        productAPIServices = retrofit.create(ProductAPIServices.class);


        fetchProductTypes();

        return rootView;

    }

  

    // Method to set the listener
    public void setOnProductTypeSelectedListener(OnProductTypeSelectedListener listener) {
        this.listener = listener;
    }

    // Method to send selected product type to the parent fragment
    private void sendSelectedProductType(String productType) {
        if (listener != null) {
            listener.onProductTypeSelected(productType);
        }
    }

    public interface FilterListener {
        void onProductTypeSelected(String productType);
        void onFilterApplied(List<Products> filteredProductsList);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Interface for communication with the parent fragment

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected product type ID
                String selectedProductTypeId = productTypeAdapter.getSelectedProductTypeId();
                if (selectedProductTypeId != null) {
                    // Call the method to filter products by the selected product type
                    filterProductsByType(selectedProductTypeId);
                } else {
                    // If no product type is selected, display a message
                    Toast.makeText(getContext(), "No product type selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng Bottom Sheet Dialog khi người dùng hủy bỏ
                dismiss();
            }
        });
    }

    private void filterProductsByType(String productTypeId) {
        Log.d(TAG, "Filtering products by product type: " + productTypeId);
        // Make an API call to filter products by product type
        Call<List<Products>> call = productAPIServices.filterProductsByType(productTypeId);
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "API call successful");
                    List<Products> filteredProducts = response.body();
                    // Check if any products are found
                    if (filteredProducts != null && !filteredProducts.isEmpty()) {
                        // Update UI with filtered products
                        filterListener.onFilterApplied(filteredProducts);
                        // Dismiss the BottomSheetDialog
                        dismiss();
                    } else {
                        // If no products are found for the specified product type, return a message
                        Toast.makeText(getContext(), "No products found for the specified product type.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "No products found for the specified product type");
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e(TAG, "Unsuccessful response: " + response.code());
                    Toast.makeText(getContext(), "Failed to filter products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                // Handle failure
                Log.e(TAG, "API call failed: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Failed to filter products", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void fetchProductTypes() {
        Call<List<ProductType>> call = productAPIServices.getProductType();
        call.enqueue(new Callback<List<ProductType>>() {
            @Override
            public void onResponse(Call<List<ProductType>> call, Response<List<ProductType>> response) {
                if (response.isSuccessful()) {
                    List<ProductType> productTypes = response.body();
                    if (productTypes != null) {
                        productTypeAdapter.setProductTypes(productTypes);
                    }
                } else {
                    Log.e("BottomSheetFragment", "Unsuccessful response: " + response.code());
                    Toast.makeText(getContext(), "Failed to fetch product types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductType>> call, Throwable t) {
                Log.e("BottomSheetFragment", "Failed to fetch product types", t);
                Toast.makeText(getContext(), "Failed to fetch product types", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
