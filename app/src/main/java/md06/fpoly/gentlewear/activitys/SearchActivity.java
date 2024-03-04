package md06.fpoly.gentlewear.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.views.fragments.HomeFragment;

public class SearchActivity extends AppCompatActivity {
    EditText et_search_product;
    ImageView btn_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        et_search_product = findViewById(R.id.et_search_product);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, HomeFragment.class);
                startActivity(intent);
            }
        });

    }
}
