package md06.fpoly.gentlewear.activitys;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.controller.Adapter.ViewpageAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_page);
        bottomNavigationView = findViewById(R.id.bot_navView);


        ViewpageAdapter adapter = new ViewpageAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.nav_cart).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.nav_heart).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.nav_user).setChecked(true);
                        break;
                }
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    viewPager.setCurrentItem(0);
                } else if (item.getItemId() == R.id.nav_cart) {
                    viewPager.setCurrentItem(1);
                } else if (item.getItemId() == R.id.nav_heart) {
                    viewPager.setCurrentItem(2);
                } else if (item.getItemId() == R.id.nav_user) {
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });


    }


}