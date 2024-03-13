package md06.fpoly.gentlewear.classs;

import android.content.Context;
import android.content.SharedPreferences;

import md06.fpoly.gentlewear.models.Users;

public class SessionManager {
    private static final String PREF_NAME = "User";
    private static final String KEY_ID = "id";
    private static final String KEY_FULL_NAME = "fullName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_IS_REMEMBER = "isRemember";
    private static final String KEY_TOKEN = "password";
    private static final String KEY_PHONE_NUMBER = "phoneNumber";
    private static final String KEY_AVATAR = "avatar";
    private static final String KEY_VAI_TRO = "vaiTro";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final Context context;
    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(Users users) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_ID, users.get_id());
        editor.putString(KEY_FULL_NAME, users.getFullname());
        editor.putString(KEY_EMAIL, users.getEmail());
        editor.putString(KEY_ADDRESS, users.getDiaChi());
        editor.putString(KEY_PHONE_NUMBER, users.getSdt());
        editor.putInt(KEY_VAI_TRO, users.getStatus());
        editor.putString(KEY_AVATAR, users.getAvatar());
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public boolean isRemember() {
        return sharedPreferences.getBoolean(KEY_IS_REMEMBER, false);
    }
    public void setIsRemember(boolean check) {
        editor.putBoolean(KEY_IS_REMEMBER, check);
        editor.apply();
    }
    public void setAddress(String address){
        editor.putString(KEY_ADDRESS,address);
        editor.apply();
    }
    public void setPhoneNumber(String phoneNumber){
        editor.putString(KEY_PHONE_NUMBER,phoneNumber);
        editor.apply();
    }
    public void setFullName(String name){
        editor.putString(KEY_FULL_NAME,name);
        editor.apply();
    }
    public void checkLogin() {
        if (!isLoggedIn()) {
            // Redirect to the login screen if not logged in
            // (You can implement an Intent to open the login screen)
        }
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
        // Redirect to the login screen after logout
        // (You can implement an Intent to open the login screen)
    }

    public Users getUsers(){
        Users users = new Users();
        users.set_id(sharedPreferences.getString(KEY_ID, ""));
        users.setFullname(sharedPreferences.getString(KEY_FULL_NAME, ""));
        users.setEmail(sharedPreferences.getString(KEY_EMAIL, ""));
        users.setSdt(sharedPreferences.getString(KEY_PHONE_NUMBER, ""));
        users.setDiaChi(sharedPreferences.getString(KEY_ADDRESS, ""));
        users.setPasswd(sharedPreferences.getString(KEY_TOKEN, ""));
        users.setStatus(sharedPreferences.getInt(KEY_VAI_TRO, 1));
        users.setAvatar(sharedPreferences.getString(KEY_AVATAR, ""));
        return users;
    }
    public String getIdUser() {
        return sharedPreferences.getString(KEY_ID, "");
    }
    public String getFullName() {
        return sharedPreferences.getString(KEY_FULL_NAME, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getPhoneNumber() {
        return sharedPreferences.getString(KEY_PHONE_NUMBER, "");
    }
    public String getAddress() {
        return sharedPreferences.getString(KEY_ADDRESS, "");
    }

    public int getVaiTro() {
        return sharedPreferences.getInt(KEY_VAI_TRO, 1);
    }
    public String getAvatar() {
        return sharedPreferences.getString(KEY_AVATAR, "");
    }
}
