package md06.fpoly.gentlewear.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.activitys.DetailProductsActivity;
import md06.fpoly.gentlewear.apiServices.Favorite_API_Services;
import md06.fpoly.gentlewear.apiServices.Next_interface;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.controller.Adapter.FavoriteAdapter;
import md06.fpoly.gentlewear.models.Messages;
import md06.fpoly.gentlewear.models.Products;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WishlistFragment extends Fragment {
    private FavoriteAdapter adapter;
    private RecyclerView recyclerView;
    private Favorite_API_Services apiServices;
    private SessionManager sessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wishlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview_favorite);

        apiServices = RetrofitClientAPI.getRetrofitInstance().create(Favorite_API_Services.class);
        sessionManager = new SessionManager(getContext());
        adapter = new FavoriteAdapter(getContext(), new Next_interface() {
            @Override
            public void onNextPage(Products products) {
                Intent intent = new Intent();
                intent.putExtra("product_data",products);
                startActivity(intent);
            }
            @Override
            public void onClickItem(String id) {
                deleteItem(id);
            }
        });

    }

    private void deleteItem(String id) {
        Call<Messages> call = apiServices.toggleFavorite(id, sessionManager.getIdUser());
        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if (response.isSuccessful()){
                    Messages msg = response.body();
                    Toast.makeText(getContext(), msg.getMsg(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Đã có lỗi xảy ra, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối server", Toast.LENGTH_SHORT).show();

            }
        });
    }
}