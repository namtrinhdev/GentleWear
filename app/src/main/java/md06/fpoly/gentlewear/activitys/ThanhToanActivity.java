package md06.fpoly.gentlewear.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.ThanhToanAPI_Interface;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.controller.Adapter.Adapter_DonHang;
import md06.fpoly.gentlewear.controller.Adapter.Adapter_DonHang;

import md06.fpoly.gentlewear.models.Cart2;
import md06.fpoly.gentlewear.models.Messages;
import md06.fpoly.gentlewear.models.ThanhToan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToanActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private FrameLayout btn_address, btn_choose_thanhToan;
    private RelativeLayout view_viTien;
    private Button btn_Dat_Hang;
    private TextView tv_address, tv_pt_thanhToan, tv_soDuHT, tv_tongDonHang, tv_soDuMoi, tv_Total_Price, tv_msg, tv_title;
    private boolean tag;
    private ThanhToanAPI_Interface mInterface;
    private Adapter_DonHang adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
//        anhxa();
//        //khoi tao
//        sessionManager = new SessionManager(this);
//        mInterface = RetrofitClientAPI.getRetrofitInstance().create(ThanhToanAPI_Interface.class);
//
//        //set dia chi
//        if (sessionManager.getAddress().equals("")) {
//            tv_address.setText("Thêm địa chỉ giao hàng");
//        } else {
//            tv_address.setText(sessionManager.getAddress());
//        }
//        btn_address.setOnClickListener(view -> {
//            if (sessionManager.getAddress().equals("")) {
//                startActivity(new Intent(ThanhToanActivity.this, InformationActivity.class));
//            }
//        });
//
//        //hien thi danh sach don hang
//        adapter = new Adapter_DonHang(this);
//        adapter.setData(Cart2.getInstance().getCart());
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setAdapter(adapter);
//
//        //hide wallet, set total price
//        setVisibilityVi(false);
//        tv_Total_Price.setText(Cart2.instance.getTotalPrice() + " đ");
//
//        //click option thanh toan
//        tag = true;
//        btn_choose_thanhToan.setOnClickListener(view -> {
//            openDialogBottom();
//        });
//
//        //back press
        findViewById(R.id.btn_back_thanhToan).setOnClickListener(view -> {
            onBackPressed();
        });
//
//        //click dat hang
//        btn_Dat_Hang.setOnClickListener(view -> {
//            if (sessionManager.getAddress().equals("")) {
//                Toast.makeText(this, "Vui lòng thêm địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
//            } else {
//                if (!tag) {
//                    if (!checkMoney()) {
//                        Toast.makeText(this, "Số dư không đủ, vui lòng chọn phương thức thanh toán khác", Toast.LENGTH_SHORT).show();
//                    } else {
//                        postData();
//                        sessionManager.setMoney(sessionManager.getMoney() - Cart2.getInstance().getTotalPrice());
//                        Cart2.getInstance().clear();
//                        startActivity(new Intent(ThanhToanActivity.this, QLDH_Activity.class));
//                        finish();
//                    }
//                } else {
//                    postData();
//                    Cart2.getInstance().clear();
//                    startActivity(new Intent(ThanhToanActivity.this, QLDH_Activity.class));
//                    finish();
//                }
//            }
//        });
    }

//    private void postData() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        String thoiGian = sdf.format(new Date());
//        ThanhToan data = new ThanhToan();
//        data.setCart(Cart2.getInstance().getCart());
//        data.setUser(sessionManager.getUsers());
//        data.setTongTien(Cart2.getInstance().getTotalPrice());
//        data.setThoiGian(thoiGian);
//        data.setPayOptions(optionPayment());
//        Call<Messages> call = mInterface.postDonHang(data);
//        call.enqueue(new Callback<Messages>() {
//            @Override
//            public void onResponse(Call<Messages> call, Response<Messages> response) {
//                if (response.isSuccessful()) {
//                    Messages msg = response.body();
//                    Toast.makeText(ThanhToanActivity.this, msg.getMsg(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.e("Failure post", "onFailure: ");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Messages> call, Throwable t) {
//                Log.e("Failure post", "onFailure: ", t);
//                Toast.makeText(ThanhToanActivity.this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private int optionPayment() {
//        if (checkMoney()) {
//            return tag ? 0 : 1;
//        }
//        return 0;
//    }
//
//    private boolean checkMoney() {
//        return Cart2.getInstance().getTotalPrice() > sessionManager.getMoney() ? false : true;
//    }
//
//    private void anhxa() {
//        tv_address = findViewById(R.id.tv_address_thanhToan);
//        tv_pt_thanhToan = findViewById(R.id.tv_phuongThuc_thanhToan);
//        tv_soDuHT = findViewById(R.id.tv_soDu_thanhToan);
//        tv_tongDonHang = findViewById(R.id.tv_soTienPhai_thanhToan);
//        tv_soDuMoi = findViewById(R.id.tv_finalMoney_thanhToan);
//        tv_Total_Price = findViewById(R.id.tv_price_thanhToan);
//        tv_msg = findViewById(R.id.tv_msg_thanhToan);
//        btn_address = findViewById(R.id.id_address_thanhToan);
//        btn_choose_thanhToan = findViewById(R.id.btn_option_thanhToan);
//        btn_Dat_Hang = findViewById(R.id.btn_datHang);
//        recyclerView = findViewById(R.id.id_recycle_thanhToan);
//        tv_title = findViewById(R.id.tv_title_money_thanhToan);
//        view_viTien = findViewById(R.id.id_yourMoney_thanhToan);
//    }
//
//    private void setVisibilityVi(boolean visible) {
//        if (visible) {
//            int totalPrice = Cart2.getInstance().getTotalPrice();
//            tv_title.setVisibility(View.VISIBLE);
//            view_viTien.setVisibility(View.VISIBLE);
//            tv_soDuHT.setText(sessionManager.getMoney() + " đ");
//            tv_tongDonHang.setText(totalPrice + " đ");
//            tv_soDuMoi.setText(sessionManager.getMoney() - totalPrice + " đ");
//            if (sessionManager.getMoney() < totalPrice) {
//                tv_msg.setVisibility(View.VISIBLE);
//            } else {
//                tv_msg.setVisibility(View.INVISIBLE);
//            }
//        } else {
//            tv_title.setVisibility(View.INVISIBLE);
//            view_viTien.setVisibility(View.INVISIBLE);
//            tv_msg.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    private void openDialogBottom() {
//        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
//        BottomSheetDialog dialog = new BottomSheetDialog(this);
//        dialog.setContentView(view);
//        dialog.show();
////        bottomSheetDialog.setCancelable(false);//tat cancel
//        RadioGroup radioGroup = view.findViewById(R.id.rd_group_thanhToan);
//        RadioButton btn_cash = view.findViewById(R.id.rd_cash);
//        RadioButton btn_wallet = view.findViewById(R.id.rd_wallet);
//        if (tag) {
//            btn_cash.setChecked(true);
//        } else {
//            btn_wallet.setChecked(true);
//        }
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch (i) {
//                    case R.id.rd_cash:
//                        setVisibilityVi(false);
//                        tv_pt_thanhToan.setText("Thanh toán bằng tiền mặt");
//                        tag = true;
//                        dialog.dismiss();
//                        break;
//                    case R.id.rd_wallet:
//                        setVisibilityVi(true);
//                        tv_pt_thanhToan.setText("Thanh toán bằng tài khoản ví");
//                        tag = false;
//                        dialog.dismiss();
//                        break;
//                }
//            }
//        });
//    }
}