package md06.fpoly.gentlewear.apiServices;

import java.util.List;


import md06.fpoly.gentlewear.models.Messages;
import md06.fpoly.gentlewear.models.NapTien;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface NapTienInterface {
    @GET("api/naptien/waitconfirm")
    Call<List<NapTien>> getAllDonChoXacNhan();
    @GET("api/naptien/confirmed")
    Call<List<NapTien>> getAllDonDaXacNhan();
    @GET("api/naptien/canceled")
    Call<List<NapTien>> getAllDonDaHuy();
    @GET("api/naptien/waitconfirm/{idUser}")
    Call<List<NapTien>> getAllDonChoXacNhanById(@Path("idUser") String iduser);
    @GET("api/naptien/confirmed/{idUser}")
    Call<List<NapTien>> getAllDonDaXacNhanById(@Path("idUser") String iduser);
    @GET("api/naptien/canceled/{idUser}")
    Call<List<NapTien>> getAllDonDaHuyById(@Path("idUser") String iduser);
    @Multipart
    @POST("api/naptien/post")
    Call<Messages> postRequestNapTien(@Part MultipartBody.Part imgGD,
                                      @Part("userModel") RequestBody userModel,
                                      @Part("soTienNap") RequestBody soTienNap,
                                      @Part("thoiGian") RequestBody thoiGian);
    @FormUrlEncoded
    @PUT("api/naptien/put/{id}")
    Call<Messages> putRequestNapTien(@Path("id") String _id,
                                     @Field("vaitro") int vaitro,
                                     @Field("thoiGian") String thoiGian);
}
