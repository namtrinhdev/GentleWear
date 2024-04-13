package md06.fpoly.gentlewear.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.databinding.ActivityDetailDonNapBinding;
import md06.fpoly.gentlewear.models.NapTien;


public class DetailDonNapActivity extends AppCompatActivity {
    private NapTien data = new NapTien();
    private ActivityDetailDonNapBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_don_nap);

        Intent intent = getIntent();
        data = (NapTien) intent.getSerializableExtra("naptien");

        setDefaultVisibility();
        setDataView();
        checkTrangThaiDonNap();

        binding.imgBackpressDetailDonnap.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void checkTrangThaiDonNap() {
        if (data.getTrangThai() == 2){
            binding.daxacnhanDetailDonNap.setVisibility(View.VISIBLE);
            binding.tvTimeDaXacNhanDetailDonNap.setText(data.getStatusUpdates().get(1).getTime());
        }else if(data.getTrangThai() == 0){
            binding.dahuyDetailDonNap.setVisibility(View.VISIBLE);
            binding.tvTimeDaHuyDetailDonNap.setText(data.getStatusUpdates().get(1).getTime());
        }
    }

    private void setDataView() {
        String url = APIClass.URL + "uploads/" +data.getUserModel().getAvatar();
        String url1 = APIClass.URL + "uploads/" +data.getAnhGiaoDich();
        binding.tvMoneyDetailDonnap.setText(String.valueOf(data.getSoTienNap()));
        binding.tvNameUserDetailDonNap.setText(data.getUserModel().getFullname());
        Glide.with(this).load(url).placeholder(R.drawable.img_default_user).error(R.drawable.img_default_user).into(binding.imgUserDetailDonNap);
        Glide.with(this).load(url1).placeholder(R.drawable.img_default_image).error(R.drawable.img_default_image).into(binding.imgGDDetailDonNap);
        binding.tvTimeChoXacNhanDetailDonNap.setText(data.getStatusUpdates().get(0).getTime());
        binding.tvMaDonNap.setText("Mã đơn nạp :  "+data.get_id());
    }

    private void setDefaultVisibility() {
        binding.choxacnhanDetailDonNap.setVisibility(View.VISIBLE);
        binding.daxacnhanDetailDonNap.setVisibility(View.GONE);
        binding.dahuyDetailDonNap.setVisibility(View.GONE);
    }
}