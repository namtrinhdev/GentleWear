package md06.fpoly.gentlewear.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;


import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.controller.Adapter.Adapter_DonHang;
import md06.fpoly.gentlewear.databinding.ActivityDetailDonHangBinding;
import md06.fpoly.gentlewear.models.ThanhToan;


public class DetailDonHangActivity extends AppCompatActivity {
    private ActivityDetailDonHangBinding binding;
    private ThanhToan data = new ThanhToan();
    private Adapter_DonHang adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_don_hang);

        Intent intent = getIntent();
        data = (ThanhToan) intent.getSerializableExtra("donhang");

        setDefaultVisibility();
        setDataView();
        checkTrangThaiDonNap();

        binding.imgBackpressDetailDonhang.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setDefaultVisibility() {
        binding.choxacnhanDetailDonHang.setVisibility(View.VISIBLE);
        binding.choLayHangDetailDonHang.setVisibility(View.GONE);
        binding.dangGiaoDetailDonHang.setVisibility(View.GONE);
        binding.daGiaoDetailDonHang.setVisibility(View.GONE);
        binding.daHuyDetailDonHang.setVisibility(View.GONE);
    }

    private void setDataView() {
        String url = APIClass.URL + "uploads/" +data.getUser().getAvatar();
        binding.tvNameUserDetailDonHang.setText(data.getUser().getFullname());
        Glide.with(this).load(url).placeholder(R.drawable.img_default_user).error(R.drawable.img_default_user).into(binding.imgUserDetailDonHang);
        binding.tvTimeChoXacNhanDetailDonHang.setText(data.getStatusUpdates().get(0).getTime());
        binding.tvMaDonHang.setText("Mã đơn nạp :  "+data.get_id());
        getDataFood();
    }

    private void getDataFood() {
        adapter = new Adapter_DonHang(this);
        adapter.setData(data.getCart());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        binding.recyclerView.setAdapter(adapter);
    }

    private void checkTrangThaiDonNap() {
        if (data.getTrangThai() == 2){
            binding.choLayHangDetailDonHang.setVisibility(View.VISIBLE);
            binding.tvTimeChoLayHangDetailDonhang.setText(data.getStatusUpdates().get(1).getTime());
        } else if (data.getTrangThai() == 3) {
            binding.choLayHangDetailDonHang.setVisibility(View.VISIBLE);
            binding.tvTimeChoLayHangDetailDonhang.setText(data.getStatusUpdates().get(1).getTime());
            binding.dangGiaoDetailDonHang.setVisibility(View.VISIBLE);
            binding.tvTimeDangGiaoDetailDonHang.setText(data.getStatusUpdates().get(2).getTime());
        } else if (data.getTrangThai() == 4) {
            binding.choLayHangDetailDonHang.setVisibility(View.VISIBLE);
            binding.tvTimeChoLayHangDetailDonhang.setText(data.getStatusUpdates().get(1).getTime());
            binding.dangGiaoDetailDonHang.setVisibility(View.VISIBLE);
            binding.tvTimeDangGiaoDetailDonHang.setText(data.getStatusUpdates().get(2).getTime());
            binding.daGiaoDetailDonHang.setVisibility(View.VISIBLE);
            binding.tvTimeDaGiaoDetailDonHang.setText(data.getStatusUpdates().get(3).getTime());
        } else if (data.getTrangThai() == 0){
            binding.daHuyDetailDonHang.setVisibility(View.VISIBLE);
            binding.tvTimeDaHuyDetailDonHang.setText(data.getStatusUpdates().get(1).getTime());
        }
    }
}