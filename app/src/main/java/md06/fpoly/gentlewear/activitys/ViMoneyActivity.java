package md06.fpoly.gentlewear.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.UserInterface;
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.models.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViMoneyActivity extends AppCompatActivity {
    private TextView tv_so_du;
    private ImageView img_sh_so_du;
    private SessionManager sessionManager;
    private UserInterface mInterface;
    private final String TAG = "onTEST";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vi_money);
        tv_so_du = findViewById(R.id.tv_so_du_tai_khoan);
        img_sh_so_du = findViewById(R.id.img_show_hide_so_du);
        sessionManager = new SessionManager(this);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIClass.URL).addConverterFactory(GsonConverterFactory.create()).build();
        mInterface = retrofit.create(UserInterface.class);


        findViewById(R.id.btn_trangthai_don_nap).setOnClickListener(view -> {
            startActivity(new Intent(ViMoneyActivity.this, QLDonNapActivity.class));
        });

        findViewById(R.id.btn_naptien).setOnClickListener(view -> {
            startActivity(new Intent(ViMoneyActivity.this, NapTienActivity.class));
        });

        findViewById(R.id.btn_lichsuGD).setOnClickListener(view -> {

        });
        //ẩn hiện money
        img_sh_so_du.setImageResource(R.drawable.view);
        img_sh_so_du.setOnClickListener(v->{
            loadMoney();
        });
        tv_so_du.setTag(true);
        tv_so_du.setText("*****");

        //nút quay lại
        findViewById(R.id.img_backpress_wallet).setOnClickListener(v ->{
            onBackPressed();
        });
    }

    private void loadMoney() {
        Call<Users> call = mInterface.getUserById(sessionManager.getIdUser());
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()){
                    Users users = response.body();
                    sessionManager.setMoney(users.getSoTien());
                    String text = users.getSoTien()+" VNĐ";
                    boolean isMoneyVisible = tv_so_du.getTag() == null || (boolean) tv_so_du.getTag();
                    if (isMoneyVisible) {
                        tv_so_du.setTag(false);
                        tv_so_du.setText(text);
                        img_sh_so_du.setImageResource(R.drawable.eye);
                    } else {
                        tv_so_du.setTag(true);
                        tv_so_du.setText("*****");
                        img_sh_so_du.setImageResource(R.drawable.view);
                    }
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMoney();
    }
}