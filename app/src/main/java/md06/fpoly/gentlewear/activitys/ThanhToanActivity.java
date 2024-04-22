package md06.fpoly.gentlewear.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
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

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.Cart_Update_Interface;
import md06.fpoly.gentlewear.apiServices.ThanhToanAPI_Interface;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.classs.SessionManager;

import md06.fpoly.gentlewear.controller.Adapter.CartAdapter;
import md06.fpoly.gentlewear.models.Cart2;
import md06.fpoly.gentlewear.models.CreateOrder;
import md06.fpoly.gentlewear.models.Messages;
import md06.fpoly.gentlewear.models.ThanhToan;
import md06.fpoly.gentlewear.utils.AppInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private FrameLayout btn_address, btn_Dat_Hang, btn_choose_thanhToan;
    private RelativeLayout view_viTien;
    private TextView tv_address, tv_pt_thanhToan, tv_Total_Price;
    private int tag;
    private TextView tv_soDuHT, tv_tongDonHang, tv_soDuMoi, tv_msg, tv_title;
    private ThanhToanAPI_Interface mInterface;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        anhxa();
        sessionManager = new SessionManager(this);
        mInterface = RetrofitClientAPI.getRetrofitInstance().create(ThanhToanAPI_Interface.class);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
        // handle CreateOrder
        // Set dia chi giao hang
        if (sessionManager.getAddress().equals("")) {
            tv_address.setText("Thêm địa chỉ giao hàng");
        } else {
            tv_address.setText(sessionManager.getAddress());
        }

        btn_address.setOnClickListener(view -> {
            if (sessionManager.getAddress().equals("")) {
                startActivity(new Intent(ThanhToanActivity.this, InformationActivity.class));
            }
        });

        // Hien thi danh sach don hang
        loadListCart();


        // Hide wallet, set total price
        tv_Total_Price.setText(Cart2.getInstance().getTotalPrice() + " đ");

        // Click option thanh toan
        setDefaultTextViewPaymentMethod();
        btn_choose_thanhToan.setOnClickListener(view -> {
            openDialogBottom();
        });

        // Back press
        findViewById(R.id.btn_back_thanhToan).setOnClickListener(view -> {
            onBackPressed();
        });

        btn_Dat_Hang.setOnClickListener(view -> {
            if (sessionManager.getAddress().equals("")) {
                Toast.makeText(this, "Vui lòng thêm địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
            } else {
                if (tag == 0) {
                    postData(0);
                    startActivity(new Intent(ThanhToanActivity.this, QLDH_Activity.class));
                    finish();
                } else {
                    //
                    createTokenOrder();
                    // Xu ly khi bam nut Dat Hang
                }
            }
        });
    }

    private void loadListCart() {
        adapter = new CartAdapter(this, new Cart_Update_Interface() {
            @Override
            public void onDelete(int position) {

            }

            @Override
            public void onUpdateCount() {

            }
        });
        adapter.setData(Cart2.getInstance().getCart());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void createTokenOrder() {
        CreateOrder orderApi = new CreateOrder();
        try {
            // Pass total price as a string
            JSONObject data = orderApi.createOrder(String.valueOf(Cart2.getInstance().getTotalPrice()));
            String code = data.getString("return_code");
            Toast.makeText(getApplicationContext(), "Tạo đơn hàng thành công", Toast.LENGTH_LONG).show();

            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                startPayWithZaloPay(token);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startPayWithZaloPay(String token) {
        ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
            @Override
            public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                runOnUiThread(() -> new AlertDialog.Builder(ThanhToanActivity.this)
                        .setTitle("Payment Success")
                        .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
                        .setPositiveButton("OK``", (dialog, which) -> {
                        })
                        .setNegativeButton("Cancel", null).show());
                postData(1);
                startActivity(new Intent(ThanhToanActivity.this, QLDH_Activity.class));
                finish();
            }

            @Override
            public void onPaymentCanceled(String zpTransToken, String appTransID) {
                Toast.makeText(ThanhToanActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                Toast.makeText(ThanhToanActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postData(int optionPay) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String thoiGian = sdf.format(new Date());
        ThanhToan data = new ThanhToan();
        data.setCart(Cart2.getInstance().getCart());
        data.setUser(sessionManager.getUsers());
        data.setTongTien(Cart2.getInstance().getTotalPrice());
        data.setThoiGian(thoiGian);
        data.setPayOptions(optionPay);
        Call<Messages> call = mInterface.postDonHang(data);

        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if (response.isSuccessful()) {
                    Messages msg = response.body();
                    Toast.makeText(ThanhToanActivity.this, msg.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Failure post", "onFailure: ");
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Log.e("Failure post", "onFailure: ", t);
                Toast.makeText(ThanhToanActivity.this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean checkMoney() {
        return Cart2.getInstance().getTotalPrice() <= sessionManager.getMoney();
    }

    private void anhxa() {
        tv_address = findViewById(R.id.tv_address_thanhToan);
        tv_pt_thanhToan = findViewById(R.id.tv_phuongThuc_thanhToan);
        tv_Total_Price = findViewById(R.id.tv_price_thanhToan);
        btn_address = findViewById(R.id.id_address_thanhToan);
        btn_Dat_Hang = findViewById(R.id.btn_dat_hang);
        recyclerView = findViewById(R.id.id_recycle_thanhToan);
        btn_choose_thanhToan = findViewById(R.id.btn_option_thanhToan);

        tv_soDuHT = findViewById(R.id.tv_soDu_thanhToan);
        tv_tongDonHang = findViewById(R.id.tv_soTienPhai_thanhToan);
        tv_soDuMoi = findViewById(R.id.tv_finalMoney_thanhToan);
        tv_msg = findViewById(R.id.tv_msg_thanhToan);

        tv_title = findViewById(R.id.tv_title_money_thanhToan);
        view_viTien = findViewById(R.id.id_yourMoney_thanhToan);
    }

    private void setVisibilityVi(boolean visible) {
        if (visible) {
            int totalPrice = Cart2.getInstance().getTotalPrice();
            tv_title.setVisibility(View.VISIBLE);
            view_viTien.setVisibility(View.VISIBLE);
            tv_soDuHT.setText(sessionManager.getMoney() + " đ");
            tv_tongDonHang.setText(totalPrice + " đ");
            tv_soDuMoi.setText(sessionManager.getMoney() - totalPrice + " đ");
            if (sessionManager.getMoney() < totalPrice) {
                tv_msg.setVisibility(View.VISIBLE);
            } else {
                tv_msg.setVisibility(View.INVISIBLE);
            }
            Log.d("Số dư người dùng", "Số dư: " + sessionManager.getMoney());
        } else {
            tv_title.setVisibility(View.INVISIBLE);
            view_viTien.setVisibility(View.INVISIBLE);
            tv_msg.setVisibility(View.INVISIBLE);
        }
    }

    private void openDialogBottom() {
        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_tt, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
//        bottomSheetDialog.setCancelable(false);//tat cancel
        TextView tv_cash = view.findViewById(R.id.tv_cash);
        TextView tv_zaloPay = view.findViewById(R.id.tv_zalopay);

        tv_cash.setOnClickListener(v -> {
            setDefaultTextViewPaymentMethod();
            dialog.dismiss();
        });
        tv_zaloPay.setOnClickListener(v -> {
            tv_pt_thanhToan.setText(tv_zaloPay.getText().toString());
            tv_pt_thanhToan.setBackgroundResource(R.drawable.shape_zalopayment);
            tv_pt_thanhToan.setTextColor(getResources().getColor(R.color.zaloColor));
            tag = 1;
            dialog.dismiss();
        });
    }
    private void setDefaultTextViewPaymentMethod(){
        tv_pt_thanhToan.setBackgroundResource(R.drawable.shape_item);
        tv_pt_thanhToan.setText("Thanh toán sau khi nhận hàng");
        tv_pt_thanhToan.setTextColor(getResources().getColor(R.color.bg_btn));
        tag = 0;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}