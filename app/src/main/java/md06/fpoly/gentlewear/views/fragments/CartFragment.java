package md06.fpoly.gentlewear.views.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.activitys.ThanhToanActivity;
import md06.fpoly.gentlewear.apiServices.Cart_Update_Interface;
import md06.fpoly.gentlewear.controller.Adapter.CartAdapter;
import md06.fpoly.gentlewear.models.Cart;
import md06.fpoly.gentlewear.models.Cart2;


public class CartFragment extends Fragment {
    private FrameLayout btn_thanhtoan;
    private RecyclerView recyclerView_cart;
    private TextView tv_price_cart,txt;
    private CartAdapter adapter;
    private List<Cart> list;
    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_thanhtoan = view.findViewById(R.id.btn_placeoder);
        recyclerView_cart = view.findViewById(R.id.recyclerview_cart);
        tv_price_cart = view.findViewById(R.id.tv_price_cart);
        //onclick
        list = Cart2.getInstance().getCart();
        tv_price_cart.setText(Cart2.getInstance().getTotalPrice() + " đ");


        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() !=0){
                    Intent i = new Intent(getActivity(), ThanhToanActivity.class);
                    startActivity(i);
                }
            }
        });

        adapter = new CartAdapter(getContext(), new Cart_Update_Interface() {
            @Override
            public void onDelete(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có muốn xóa khỏi giỏ hàng ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(position);
                        onResume();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }

            @Override
            public void onUpdateCount() {
                tv_price_cart.setText(Cart2.getInstance().getTotalPrice() + " đ");
            }
        });
        loadData();
        //---------------------------------end--------------
    }
    private void loadData() {
        adapter.setData((ArrayList<Cart>) list);
        recyclerView_cart.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView_cart.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        tv_price_cart.setText(Cart2.getInstance().getTotalPrice() + " đ");
        loadData();
    }
}