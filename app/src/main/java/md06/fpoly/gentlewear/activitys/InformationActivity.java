package md06.fpoly.gentlewear.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.UserInterface;
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.classs.RetrofitClientAPI;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.models.Users;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationActivity extends AppCompatActivity {
    private static final String TAG = "InfoUser";
    private SessionManager sessionManager;
    private TextView tvName, tvEmail, tvPhone, tvAddress;
    private ImageView imgAvatar;
    private LinearLayout edName, edAddress, ed_phone;
    private UserInterface userInterface;
    private EditText edPass;
    private String imagePath;
    private Uri selectedImageUri;
    private static final int REQUEST_CODE_CHOOSE_IMAGE = 1;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        anhXa();
        //khoitao
        sessionManager = new SessionManager(this);
        userInterface = RetrofitClientAPI.getRetrofitInstance().create(UserInterface.class);
        setData();

        //click chon anh
        String url = APIClass.URL + "uploads/" + sessionManager.getAvatar();
        if (!sessionManager.getAvatar().equals("")) {
            Glide.with(InformationActivity.this).load(url).placeholder(R.drawable.img_default_user).into(imgAvatar);
        }
        imgAvatar.setOnClickListener(view -> {
            checkStoragePermissionAndOpenImageChooser();
        });

        //click back
        findViewById(R.id.img_backpress_infoUser).setOnClickListener(view -> {
            onBackPressed();
        });

        //click save
        findViewById(R.id.btn_save_infoUser).setOnClickListener(view -> {
            saveData();
        });

        //click phone number
        ed_phone.setOnClickListener(view -> {
            if (sessionManager.getPhoneNumber().equals("")) {
                String title = "Thêm số điện thoại";
                openDialogUpdate(title, sessionManager.getPhoneNumber(), tvPhone);
            }
        });

        //click address
        edAddress.setOnClickListener(view -> {
            String title = "Sửa địa chỉ";
            openDialogUpdate(title, sessionManager.getAddress(), tvAddress);
        });

        //click name
        edName.setOnClickListener(view -> {
            String title = "Sửa tên";
            openDialogUpdate(title, sessionManager.getFullName(),tvName);
        });

        //click pass
        edPass.setOnClickListener(view -> {
            openDialogChangePass();
        });
    }

    private void openDialogChangePass() {
        Dialog dialog = new Dialog(InformationActivity.this);
        dialog.setContentView(R.layout.dialog_change_password);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        EditText edOldPass = dialog.findViewById(R.id.ed_oldPass_dialog_changePass);
        EditText edNewPass = dialog.findViewById(R.id.ed_newPass_dialog_changePass);
        EditText edReNewPass = dialog.findViewById(R.id.ed_reNewPass_dialog_changePass);


        Button btnConfirm = dialog.findViewById(R.id.btn_confirm_changePass_dialog);

        btnConfirm.setOnClickListener(view -> {
            String oldPass = edOldPass.getText().toString().trim();
            String newPass = edNewPass.getText().toString().trim();
            String rePass = edReNewPass.getText().toString().trim();
            Log.i(TAG, "openDialogChangePass: "+oldPass+" "+newPass +" "+rePass);
            if (oldPass.equals("") || newPass.equals("") || rePass.equals("")){
                Toast.makeText(this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }else {
                if (!oldPass.equals(sessionManager.getToken())){
                    Toast.makeText(this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                }else if (newPass.length() < 8 || newPass.length() > 16){
                    Toast.makeText(this, "Mật khẩu mới dài 8-16 ký tự", Toast.LENGTH_SHORT).show();
                }else if (!newPass.equals(rePass)){
                    Toast.makeText(this, "Mật khẩu nhập lại không trùng khớp", Toast.LENGTH_SHORT).show();
                }else {
                    edPass.setText(newPass);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void openDialogUpdate(String title, String data, TextView tv) {
        Dialog dialog = new Dialog(InformationActivity.this);
        dialog.setContentView(R.layout.dialog_update_user);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.show();
        EditText edUpdate = dialog.findViewById(R.id.ed_dialog_user);
        TextView tv_title = dialog.findViewById(R.id.tv_title_dialog_user);
        Button btnConfirm = dialog.findViewById(R.id.btn_confirm_user_dialog);

        tv_title.setText(title);
        edUpdate.setText(data);

        btnConfirm.setOnClickListener(v -> {
            if (edUpdate.getText().toString().trim().equals("")) {
                Toast.makeText(this, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
            } else {
                tv.setText(edUpdate.getText().toString());
                dialog.dismiss();
            }
        });
    }

    private void saveData() {
        if (tvPhone.getText().toString().equals("Thêm số điện thoại") || tvAddress.getText().toString().equals("Thêm địa chỉ")) {
            Toast.makeText(this, "Thêm địa chỉ và số điện thoại", Toast.LENGTH_SHORT).show();
        } else {
            MultipartBody.Part bodyimg;
            if (imagePath == null) {
                bodyimg = null;
            }else {
                File file = new File(imagePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                bodyimg = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
            }
            Users users = new Users();
            users.setFullname(tvName.getText().toString());
            users.setDiaChi(tvAddress.getText().toString());
            users.setSdt(tvPhone.getText().toString());
            users.setPasswd(edPass.getText().toString());
            Gson gson = new Gson();
            RequestBody bodyUser = RequestBody.create(MediaType.parse("application/json"), gson.toJson(users));

            Call<Users> call = userInterface.putUser(sessionManager.getIdUser(), bodyimg, bodyUser);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if (response.isSuccessful()) {
                        Users model = response.body();
                        sessionManager.logoutUser();
                        sessionManager.createLoginSession(model);
                        Toast.makeText(InformationActivity.this, "Đã lưu thay đổi", Toast.LENGTH_SHORT).show();
                        setData();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });
        }
    }

    private void anhXa() {
        tvName = findViewById(R.id.tv_fullname_infoUser);
        tvEmail = findViewById(R.id.tv_email_infoUser);
        tvPhone = findViewById(R.id.tv_phoneNumber_infoUser);
        tvAddress = findViewById(R.id.tv_address_infoUser);
        edPass = findViewById(R.id.ed_password_infoUser);
        ed_phone = findViewById(R.id.id_phoneNumber_infoUser);
        imgAvatar = findViewById(R.id.img_avatar_infoUser);
        edName = findViewById(R.id.btn_fullname_infoUser);
        edAddress = findViewById(R.id.id_address_infoUser);
    }

    private void setData() {
        tvName.setText(sessionManager.getFullName());
        tvEmail.setText(sessionManager.getEmail());
        tvPhone.setText(sessionManager.getPhoneNumber().equals("") ? "Thêm số điện thoại" : sessionManager.getPhoneNumber());
        String address = sessionManager.getAddress().equals("") ? "Thêm địa chỉ" : sessionManager.getAddress();
        tvAddress.setText(address);
        edPass.setText(sessionManager.getToken());
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

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), REQUEST_CODE_CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Lấy đường dẫn của ảnh được chọn
            Uri uri = data.getData();
            imagePath = getPathFromUri(uri);
            try {
                // Chuyển đổi Uri thành Bitmap và hiển thị trong ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgAvatar.setImageBitmap(bitmap);
                selectedImageUri = uri;
            } catch (Exception e) {
                e.printStackTrace();
            }
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