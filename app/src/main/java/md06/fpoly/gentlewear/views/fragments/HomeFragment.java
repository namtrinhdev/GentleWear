package md06.fpoly.gentlewear.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.activitys.DetailProductsActivity;
import md06.fpoly.gentlewear.activitys.SearchActivity;
import md06.fpoly.gentlewear.apiServices.Next_interface;
import md06.fpoly.gentlewear.apiServices.ProductAPIServices;
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.controller.Adapter.HomeAdapter;
import md06.fpoly.gentlewear.models.Products;
import md06.fpoly.gentlewear.models.ResProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFrg";
    private ImageView img_search, img_avatar;
    private ProductAPIServices apiServices;
    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private List<Products> productList;
    private HomeAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Spinner spinnerSort;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = rootView.findViewById(R.id.rv_home);
        img_search = rootView.findViewById(R.id.btnSearch);
        img_avatar = rootView.findViewById(R.id.img_avatar);
        spinnerSort = rootView.findViewById(R.id.spn_sort_home);
      swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapter = new HomeAdapter(getContext(), new Next_interface() {
            @Override
            public void onNextPage(Products products) {
                try {
                    if (getActivity() != null) {
                        // Navigate to detail screen with the selected product
                        Intent intent = new Intent(getActivity(), DetailProductsActivity.class);
                        intent.putExtra("product_data", products);
                        startActivity(intent);
                    } else {
                        Log.e(TAG, "Activity is null");
                        Toast.makeText(getContext(), "Error navigating to detail screen", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error navigating to detail screen: " + e.getMessage());
                    Toast.makeText(getContext(), "Error navigating to detail screen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClickItem(String id) {
                // Handle click on item (if needed)
            }

        });
        // Set up RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // Set up Spinner for sorting
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(spinnerAdapter);
        spinnerSort.setPrompt("PROMPT");
        // Set up Spinner item selected listener
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Handle sorting based on selected option
                String selectedOption = adapterView.getItemAtPosition(i).toString();

                // Set the selected item as the prompt for the spinner

                // Call your method to sort products based on the selected option
                sortProducts(selectedOption);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
        // Create a GridLayoutManager with two columns
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize Retrofit client
        apiServices = RetrofitClientAPI.getRetrofitInstance().create(ProductAPIServices.class);

        // Load data
        loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Call your method to reload data
                loadData();
            }
        });
        // Handle click on search button
        img_search.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });

        // Load avatar image
        sessionManager = new SessionManager(getContext());
        if (!sessionManager.getAvatar().equals("")) {
            Glide.with(this).load(sessionManager.getAvatar()).apply(RequestOptions.centerCropTransform()).into(img_avatar);

        }
    }

    private void loadData() {
        Call<ResProduct> call = apiServices.getProduct(1, 10);
        call.enqueue(new Callback<ResProduct>() {
            @Override
            public void onResponse(Call<ResProduct> call, Response<ResProduct> response) {

                if (response.isSuccessful()) {
                    ResProduct resProduct = response.body();
                    if (resProduct != null) {
                        productList = resProduct.getData();
                        if (productList != null && !productList.isEmpty()) {

                            adapter.setData((ArrayList<Products>) productList);
                            adapter.notifyDataSetChanged(); // Notify adapter of data change
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    } else {
                        Log.e(TAG, "Response body is null");
                        Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Unsuccessful response: " + response.code());
                    Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResProduct> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: ", t);

                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sortProducts(String selectedOption) {
        // Define sorting criteria to be passed to the API
        String sortBy;
        switch (selectedOption) {
            case "Name (A-Z)":
                sortBy = "name_asc"; // Assuming the API expects this sorting criteria for name A-Z
                Toast.makeText(getContext(), "Đã sắp xếp tên A-Z "  ,Toast.LENGTH_SHORT).show();
                break;
            case "Price (Low to High)":
                sortBy = "price_asc";
                Toast.makeText(getContext(), "Đã sắp xếp giá từ thấp đến cao "  ,Toast.LENGTH_SHORT).show();// Assuming the API expects this sorting criteria for price low to high
                break;
            case "Price (High to Low)":
                sortBy = "price_desc";
                Toast.makeText(getContext(), "Đã sắp xếp giá từ cao đến thấp "  ,Toast.LENGTH_SHORT).show();// Assuming the API expects this sorting criteria for price high to low
                break;
            default:
                return; // Do nothing if the selected option is not recognized
        }

        // Call the API to get sorted products
        Call<ResProduct> call = apiServices.getsort(sortBy);
        call.enqueue(new Callback<ResProduct>() {
            @Override
            public void onResponse(Call<ResProduct> call, Response<ResProduct> response) {
                if (response.isSuccessful()) {
                    ResProduct sortedProductResponse = response.body();
                    if (sortedProductResponse != null && sortedProductResponse.getData() != null) {
                        List<Products> sortedProducts = sortedProductResponse.getData();
                        // Update RecyclerView with sorted data
                        adapter.setData((ArrayList<Products>) sortedProducts);

                    }
                } else {
                    // Handle unsuccessful response
                    Log.e(TAG, "Unsuccessful response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResProduct> call, Throwable t) {
                // Handle failure
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });
    }

}


