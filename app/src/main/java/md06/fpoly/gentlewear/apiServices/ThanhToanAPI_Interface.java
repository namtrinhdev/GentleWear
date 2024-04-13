package md06.fpoly.gentlewear.apiServices;

import java.util.List;

import md06.fpoly.gentlewear.models.Messages;
import md06.fpoly.gentlewear.models.ThanhToan;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ThanhToanAPI_Interface {
    @GET("api/orders/waitconfirm/{idUser}")
    Call<List<ThanhToan>> getDsChoXacNhan(@Path("idUser")String id);

    @GET("api/orders/waitfood/{idUser}")
    Call<List<ThanhToan>> getDsChoLayHang(@Path("idUser")String id);

    @GET("api/orders/delivering/{idUser}")
    Call<List<ThanhToan>> getDsDangGiao(@Path("idUser")String id);

    @GET("api/orders/delivered/{idUser}")
    Call<List<ThanhToan>> getDsDaGiao(@Path("idUser")String id);

    @GET("api/orders/canceled/{idUser}")
    Call<List<ThanhToan>> getDsDaHuy(@Path("idUser")String id);
    @POST("api/orders/post")
    Call<Messages> postDonHang(@Body ThanhToan data);
    @FormUrlEncoded
    @PUT("api/orders/put/{id}")
    Call<Messages> putDonHang(@Path("id") String id,
                              @Field("vaitro") int vaitro,
                              @Field("thoiGian") String thoiGian,
                              @Field("trangThai") int trangThai
                              );
}
