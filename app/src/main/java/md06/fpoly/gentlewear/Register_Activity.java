package md06.fpoly.gentlewear;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.interfaces.UserInterface;
import md06.fpoly.gentlewear.model.Messages;
import md06.fpoly.gentlewear.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register_Activity extends AppCompatActivity {
    private EditText fullname, email_dk, pass_dk;
    private FrameLayout btn_dk;
    private TextView txt_signin;
    private ImageView back_1;
    private UserInterface userInterface;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_signin = findViewById(R.id.txt_signin);
        fullname = findViewById(R.id.fullname_dk);
        email_dk = findViewById(R.id.email_dk);
        pass_dk =  findViewById(R.id.pass_dk);
        btn_dk = findViewById(R.id.btn_dk);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIClass.URL).addConverterFactory(GsonConverterFactory.create()).build();
        userInterface = retrofit.create(UserInterface.class);

        progressDialog = new ProgressDialog(this);

        btn_dk.setOnClickListener(view -> {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            boolean validate = true;
            if(fullname.getText().toString().trim().isEmpty()){
                fullname.setError("Hãy điền đầy đủ họ tên");
                validate = false;
            }
            if(email_dk.getText().toString().trim().isEmpty()){
                email_dk.setError("Hãy điền đầy đủ email");
                validate = false;
            }
            if(pass_dk.getText().toString().trim().isEmpty()){
                pass_dk.setError("Hãy nhập mật khẩu");
                validate = false;
            }
            if(validate){
                Users users = new Users();
                users.setFullname(fullname.getText().toString());
                users.setEmail(email_dk.getText().toString());
                users.setSdt("");
                users.setPaswd(pass_dk.getText().toString());
                users.setDiaChi("");
                users.setAvatar("");
                Call<Messages> call = userInterface.registerUser(users);
                call.enqueue(new Callback<Messages>() {
                    @Override
                    public void onResponse(Call<Messages> call, Response<Messages> response) {
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        if(response.isSuccessful()){
                            Messages msg = response.body();
                            Toast.makeText(Register_Activity.this, msg.getMsg(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register_Activity.this, Login_Activity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<Messages> call, Throwable t) {
                        Toast.makeText(Register_Activity.this, "Lỗi kết nối vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        Log.e( "onFailure register", t.toString() );
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                });
            }
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        });
        txt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
        back_1 = findViewById(R.id.back_1);
        back_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Activity.this, SplashActivity.class);
                startActivity(intent);
            }
        });
    }
}