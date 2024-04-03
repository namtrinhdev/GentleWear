package md06.fpoly.gentlewear.activitys;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.ThanhToanAPI_Interface;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.controller.Adapter.Adapter_DonHang;
import md06.fpoly.gentlewear.controller.Adapter.Adapter_DonHang;

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
    private FrameLayout btn_address, btn_choose_thanhToan, btn_Dat_Hang,btn_option_thanhToanZalo;
    private RelativeLayout view_viTien;
    private TextView tv_address, tv_pt_thanhToan, tv_Total_Price;
    private boolean tag;
    private ThanhToanAPI_Interface mInterface;
    private Adapter_DonHang adapter;
    TextView lblZpTransToken, txtToken;
    private void BindView() {
        txtToken = findViewById(R.id.txtToken);
        lblZpTransToken = findViewById(R.id.lblZpTransToken);
        IsLoading();
    }

    private void IsLoading() {
        lblZpTransToken.setVisibility(View.INVISIBLE);
        txtToken.setVisibility(View.INVISIBLE);
    }

    private void IsDone() {
        lblZpTransToken.setVisibility(View.VISIBLE);
        txtToken.setVisibility(View.VISIBLE);
    }
    String amount = "10000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        anhxa();
        sessionManager = new SessionManager(this);
        mInterface = RetrofitClientAPI.getRetrofitInstance().create(ThanhToanAPI_Interface.class);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
        BindView();
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
        adapter = new Adapter_DonHang(this);
        adapter.setData(Cart2.getInstance().getCart());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        // Hide wallet, set total price
        tv_Total_Price.setText(Cart2.getInstance().getTotalPrice() + " đ");

        // Click option thanh toan
        tag = true;
        btn_choose_thanhToan.setOnClickListener(view -> {

        });

        // Back press
        findViewById(R.id.btn_back_thanhToan).setOnClickListener(view -> {
            onBackPressed();
        });

        btn_option_thanhToanZalo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    // Pass total price as a string
                    JSONObject data = orderApi.createOrder(amount);
                    lblZpTransToken.setVisibility(View.VISIBLE);
                    String code = data.getString("return_code");
                    Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

                    if (code.equals("1")) {
                        lblZpTransToken.setText("zptranstoken");
                        txtToken.setText(data.getString("zp_trans_token"));
                        IsDone();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        // Xu ly khi bam nut Dat Hang
        btn_Dat_Hang.setOnClickListener(v -> {
            String token = txtToken.getText().toString();
            ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
                @Override
                public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(ThanhToanActivity.this)
                                    .setTitle("Payment Success")
                                    .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setNegativeButton("Cancel", null).show();
                        }
                    });
                    IsLoading();
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
        });
    }



    private void postData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String thoiGian = sdf.format(new Date());
        ThanhToan data = new ThanhToan();
        data.setCart(Cart2.getInstance().getCart());
        data.setUser(sessionManager.getUsers());
        data.setTongTien(Cart2.getInstance().getTotalPrice());
        data.setThoiGian(thoiGian);
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




    private void anhxa() {
        tv_address = findViewById(R.id.tv_address_thanhToan);
        tv_pt_thanhToan = findViewById(R.id.tv_phuongThuc_thanhToan);
        tv_Total_Price = findViewById(R.id.tv_price_thanhToan);
        btn_address = findViewById(R.id.id_address_thanhToan);
        btn_choose_thanhToan = findViewById(R.id.btn_option_thanhToan);
        btn_option_thanhToanZalo = findViewById(R.id.btn_option_thanhToanZalo);
        btn_Dat_Hang = findViewById(R.id.btn_dat_hang);
        recyclerView = findViewById(R.id.id_recycle_thanhToan);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}