package md06.fpoly.gentlewear.activitys;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.controller.Adapter.Adapter_Tab_QLDH;

public class QLDH_Activity extends AppCompatActivity {
    private Adapter_Tab_QLDH adapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qldh);
        tabLayout = findViewById(R.id.tab_QLDH);
        viewPager2 = findViewById(R.id.vp2_QLDH);

        adapter = new Adapter_Tab_QLDH(this);
        viewPager2.setAdapter(adapter);

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
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
        });
        mediator.attach();
    }
}