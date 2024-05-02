package md06.fpoly.gentlewear.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.UserInterface;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.models.Messages;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    Button btnReset, btnBack;
    EditText edtEmail;
    ProgressBar progressBar;
    //    FirebaseAuth mAuth;
    String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btnBack = findViewById(R.id.btnForgotPasswordBack);
        btnReset = findViewById(R.id.btnReset);
        edtEmail = findViewById(R.id.edtForgotPasswordEmail);
        progressBar = findViewById(R.id.forgetPasswordProgressbar);

//        mAuth = FirebaseAuth.getInstance();

        //Reset Button Listener
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = edtEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)) {
                    ResetPassword();
                } else {
                    edtEmail.setError("Email field can't be empty");
                }
            }
        });


        //Back Button Code
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void ResetPassword() {
        progressBar.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.INVISIBLE);

        // Gửi yêu cầu đặt lại mật khẩu đến máy chủ
        // Sử dụng Retrofit hoặc các thư viện HTTP khác để thực hiện yêu cầu
        // Gửi email của người dùng đến máy chủ để thực hiện quy trình đặt lại mật khẩu

        // Example using Retrofit
        UserInterface apiService = RetrofitClientAPI.getRetrofitInstance().create(UserInterface.class);
        Call<Messages> call = apiService.resetpass(strEmail);
        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if (response.isSuccessful()) {
                    // Yêu cầu đặt lại mật khẩu thành công
                    Messages apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getMsg().equals("Email sent")) {
                        // Hiển thị thông báo và chuyển hướng đến màn hình đăng nhập
                        Toast.makeText(ForgotPasswordActivity.this, "Reset password email sent", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPasswordActivity.this, Login_Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Xử lý lỗi khi gửi email
                        Toast.makeText(ForgotPasswordActivity.this, "Failed to send reset password email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Xử lý lỗi từ máy chủ
                    Toast.makeText(ForgotPasswordActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                btnReset.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                // Xử lý lỗi khi gửi yêu cầu đặt lại mật khẩu
                Toast.makeText(ForgotPasswordActivity.this, "Failed to send reset password request", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnReset.setVisibility(View.VISIBLE);
            }
        });
    }

}