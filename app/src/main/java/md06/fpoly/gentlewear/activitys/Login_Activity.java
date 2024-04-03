package md06.fpoly.gentlewear.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.UserInterface;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.models.Messages;
import md06.fpoly.gentlewear.views.fragments.CartFragment;
import md06.fpoly.gentlewear.views.fragments.ProfileFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
    TextInputLayout email, pass;
    TextInputEditText txt_email, txt_pass;
    FrameLayout btn_dn;
    TextView txt_creat, reset_pass;
    ImageView back;

    UserInterface userInterface;
    private ProgressDialog progressDialog;
    private SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_creat = findViewById(R.id.txt_creat);
        email = findViewById(R.id.layout_email_dn);
        pass = findViewById(R.id.layout_pass_dn);
        btn_dn = findViewById(R.id.btn_dn);
        txt_email = findViewById(R.id.txt_email);
        txt_pass = findViewById(R.id.txt_pass);
        reset_pass = findViewById(R.id.reset_pass);
        manager = new SessionManager(this);

        userInterface = RetrofitClientAPI.getRetrofitInstance().create(UserInterface.class);
        progressDialog = new ProgressDialog(this);
        reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, ForgotPasswordActivity.class));
                finish();
            }
        });
        btn_dn.setOnClickListener(v -> {
            progressDialog.setMessage("Login...");
            progressDialog.show();
            Call<Messages> call = userInterface.checkLogin(txt_email.getText().toString(), txt_pass.getText().toString());
            call.enqueue(new Callback<Messages>() {
                @Override
                public void onResponse(Call<Messages> call, Response<Messages> response) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Messages res = response.body();
                    if (response.isSuccessful()) {
                        if (res.getData().isLocked()){
                            Toast.makeText(Login_Activity.this, "Tài khoản này đã bị khóa, vui lòng liên hệ admin để giải quyết", Toast.LENGTH_SHORT).show();
                        }else {
                            if (res.getStatus() == 1) {
                                manager.createLoginSession(res.getData());
                                Toast.makeText(Login_Activity.this, res.getMsg(), Toast.LENGTH_SHORT).show();
                                startScreen(manager.getKeyScreen());
                            } else{
                                Toast.makeText(Login_Activity.this, res.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(Login_Activity.this, "Có lỗi xảy ra, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Messages> call, Throwable t) {
                    Toast.makeText(Login_Activity.this, "Lỗi kết nối server, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.e("onFailure", t.toString());
                }
            });
        });
        txt_creat.setOnClickListener(view -> {
            Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
            startActivity(intent);
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void startScreen(int screen){
        switch (screen){
            case 1:
                startActivity(new Intent(Login_Activity.this, CartFragment.class));
                finish();
                break;
            case 2:
                startActivity(new Intent(Login_Activity.this, ProfileFragment.class));
                finish();
                break;
            default:
                startActivity(new Intent(Login_Activity.this, MainActivity.class));
                finish();
                break;
        }
    }

}