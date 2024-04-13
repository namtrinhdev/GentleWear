package md06.fpoly.gentlewear.activitys;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.controller.Adapter.AdapterQLDH;

public class QLDH_Activity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private AdapterQLDH adapter;
    private TabLayout tabLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qldh);
        viewPager2 = findViewById(R.id.id_viewpager2_qldh);
        tabLayout = findViewById(R.id.id_tablayout_qldh);

        adapter = new AdapterQLDH(this);
        viewPager2.setAdapter(adapter);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setCustomView(R.layout.custom_tablayout);
                switch (position){
                    case 0:
                        tab.setText("Chờ xác nhận");
                        break;
                    case 1:
                        tab.setText("Chờ lấy hàng");
                        break;
                    case 2:
                        tab.setText("Đang giao");
                        break;
                    case 3:
                        tab.setText("Đã giao");
                        break;
                    case 4:
                        tab.setText("Đã hủy");
                        break;
                }
                View customView = tab.getCustomView();
                if (customView != null) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) customView.getLayoutParams();
                    params.rightMargin = 40;
                }
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.bg_red));

                // Set màu text khi không được chọn
                tabLayout.setTabTextColors(getResources().getColor(R.color.bg_white), Color.parseColor("#FF4081"));
            }
        });
        mediator.attach();

    }
}