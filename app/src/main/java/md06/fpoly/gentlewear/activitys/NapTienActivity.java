package md06.fpoly.gentlewear.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.NapTienInterface;
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.models.Messages;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NapTienActivity extends AppCompatActivity {
    private TextInputEditText ed_money;
    private FrameLayout btn_pickImg;
    private TextView tv_img;
    private Uri selectedImageUri;
    private static final int REQUEST_CODE_CHOOSE_IMAGE = 1;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 2;
    private NapTienInterface mInterface;
    private SessionManager sessionManager;
    private String imagePath;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_tien);
        btnPost = findViewById(R.id.btn_post_naptien);
        ed_money = findViewById(R.id.ed_naptien);
        btn_pickImg = findViewById(R.id.btn_pick_imgGD);
        tv_img = findViewById(R.id.tv_img_name);
        checkStoragePermissionAndOpenImageChooser();
        sessionManager = new SessionManager(this);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIClass.URL).addConverterFactory(GsonConverterFactory.create()).build();
        mInterface = retrofit.create(NapTienInterface.class);
        //click chon anh GD
        btn_pickImg.setOnClickListener(view -> {
            openImageChooser();
        });

        //click naptien
        btnPost.setOnClickListener(view -> {
            Log.e("onFailure", ""+imagePath);

            if (ed_money.getText().toString().trim().isEmpty()){
                Log.d("empty","money");
                Toast.makeText(this, "Nhập số tiền bạn muốn nạp", Toast.LENGTH_SHORT).show();
            }else if (tv_img.getText().equals("Chọn ảnh giao dịch")){
                Log.d("empty","anh");
                Toast.makeText(this, "Chọn ảnh màn hình giao dịch", Toast.LENGTH_SHORT).show();
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String thoiGian = sdf.format(new Date());
                File file = new File(imagePath);
                if (!file.exists()){
                    Log.d("file","o");
                }
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part bodyimg = MultipartBody.Part.createFormData("imgGD", file.getName(), requestFile);
                RequestBody idUser = RequestBody.create(MediaType.parse("text/plain"),sessionManager.getIdUser());
                RequestBody soTien = RequestBody.create(MediaType.parse("text/plain"),ed_money.getText().toString().trim());
                RequestBody time = RequestBody.create(MediaType.parse("text/plain"),thoiGian);
                postReQuest(bodyimg,idUser,soTien,time);
            }

        });

        //chon tien default
        findViewById(R.id.btn_100k).setOnClickListener(v ->{
            ed_money.setText("100000");
        });
        findViewById(R.id.btn_200k).setOnClickListener(v ->{
            ed_money.setText("200000");
        });
        findViewById(R.id.btn_500k).setOnClickListener(v ->{
            ed_money.setText("500000");
        });
        findViewById(R.id.btn_1000k).setOnClickListener(v ->{
            ed_money.setText("1000000");
        });
        findViewById(R.id.btn_2000k).setOnClickListener(v ->{
            ed_money.setText("2000000");
        });
        findViewById(R.id.btn_5000k).setOnClickListener(v ->{
            ed_money.setText("5000000");
        });

        //click button back
        findViewById(R.id.img_backpress_naptien).setOnClickListener(v ->{
            onBackPressed();
        });
    }

    private void checkStoragePermissionAndOpenImageChooser() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                // Nếu không có quyền, yêu cầu người dùng cấp quyền
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_REQUEST_CODE);
            } else {
                // Nếu đã có quyền, mở trình chọn ảnh
                openImageChooser();
            }
        } else {
            // Đối với các phiên bản Android dưới 6.0, không cần yêu cầu quyền động
            // Mở trình chọn ảnh
            openImageChooser();
        }
    }
    private void openImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), REQUEST_CODE_CHOOSE_IMAGE);
    }
    private void postReQuest(MultipartBody.Part bodyimg, RequestBody idUser, RequestBody soTien, RequestBody time) {
        Call<Messages> call = mInterface.postRequestNapTien(bodyimg,idUser,soTien,time);
        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if (response.isSuccessful()){
                    Messages msg = response.body();
                    Toast.makeText(NapTienActivity.this, msg.getMsg(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NapTienActivity.this, QLDonNapActivity.class));
                }else {
                    Log.e("onFailure", "fail");
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Lấy đường dẫn của ảnh được chọn
            selectedImageUri = data.getData();
            imagePath = getPathFromUri(selectedImageUri);
            tv_img.setText("Đường dẫn ảnh: " + imagePath);
        }
    }
    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(columnIndex);
        cursor.close();
        return imagePath;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, mở trình chọn ảnh
                openImageChooser();
            } else {
                // Người dùng từ chối cấp quyền, có thể thông báo hoặc thực hiện các xử lý khác
            }
        }
    }
}