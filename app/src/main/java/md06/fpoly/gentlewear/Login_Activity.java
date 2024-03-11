package md06.fpoly.gentlewear;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.interfaces.UserInterface;
import md06.fpoly.gentlewear.model.Messages;
import md06.fpoly.gentlewear.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
    TextInputLayout email, pass;
    TextInputEditText txt_email, txt_pass;
    FrameLayout btn_dn;
    TextView txt_creat;
    ImageView back;

    UserInterface userInterface;
    private ProgressDialog progressDialog;

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

        userInterface = RetrofitClientAPI.getRetrofitInstance().create(UserInterface.class);
        progressDialog = new ProgressDialog(this);

        btn_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Login...");
                progressDialog.show();
                Call<Messages> call = userInterface.checkLogin(txt_email.getText().toString(), txt_pass.getText().toString());
                call.enqueue(new Callback<Messages>() {
                    @Override
                    public void onResponse(Call<Messages> call, Response<Messages> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            Messages mList = response.body();
                            if (mList.getStatus() == 1) {
                                Toast.makeText(Login_Activity.this, "Email đăng nhập không đúng", Toast.LENGTH_SHORT).show();
                            } else if (mList.getStatus() == 2) {
                                Toast.makeText(Login_Activity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            } else if (mList.getStatus() == 3) {
                                startActivity(new Intent(Login_Activity.this, MainActivity.class));
                                finish();
                                Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login_Activity.this, "Có lỗi xảy ra, vui lòng đăng nhập lại sau", Toast.LENGTH_SHORT).show();
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
            }
        });
        txt_creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(intent);
            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, SplashActivity.class);
                startActivity(intent);
            }
        });
    }

//    private void checkLogin(String emailDn, String passDn) {
//        progressDialog.setMessage("Login...");
//        progressDialog.show();
//        Call<Messages> call = userInterface.checkLogin(emailDn, passDn);
//        call.enqueue(new Callback<Messages>() {
//            @Override
//            public void onResponse(Call<Messages> call, Response<Messages> response) {
//                if(progressDialog.isShowing()){
//                    progressDialog.dismiss();
//                }
//                if(response.isSuccessful()){
//                    Messages mList = response.body();
//                    if(mList.getStatus()==1){
//                        Toast.makeText(md06.fpoly.gentlewear.Login_Activity.this, "Email đăng nhập không đúng", Toast.LENGTH_SHORT).show();
//                    }else if(mList.getStatus()==2){
//                        Toast.makeText(md06.fpoly.gentlewear.Login_Activity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
//                    }else {
//                        startActivity(new Intent(md06.fpoly.gentlewear.Login_Activity.this, MainActivity.class));
//                        finish();
//                        Toast.makeText(md06.fpoly.gentlewear.Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(md06.fpoly.gentlewear.Login_Activity.this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
//                }
//                Log.i("TAG", "onResponse: ");
////                Toast.makeText(md06.fpoly.gentlewear.Login_Activity.this, "ok", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<Messages> call, Throwable t) {
//                Log.e("TAG", "onFailure: ", t);
//                Toast.makeText(md06.fpoly.gentlewear.Login_Activity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}