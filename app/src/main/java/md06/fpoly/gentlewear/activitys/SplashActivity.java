package md06.fpoly.gentlewear.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import md06.fpoly.gentlewear.R;

public class SplashActivity extends AppCompatActivity {
    FrameLayout btn_login, btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(view -> {
            Intent intent = new Intent(SplashActivity.this, Login_Activity.class);
            startActivity(intent);
        });
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent(SplashActivity.this, Register_Activity.class);
            startActivity(intent);
        });
    }
}