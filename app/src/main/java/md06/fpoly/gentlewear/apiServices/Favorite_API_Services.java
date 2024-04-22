package md06.fpoly.gentlewear.apiServices;

import java.util.List;

import md06.fpoly.gentlewear.models.Favorites;
import md06.fpoly.gentlewear.models.Messages;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Favorite_API_Services {
    @GET("api/favorite")
    Call<List<Favorites>> getAllFavorite(@Query("idUser") String idUser);
    @POST("api/favorite/toggle")
    Call<Messages> toggleFavorite(@Query("idSp") String idSp, @Query("idUser") String idUser);
    @POST("api/favorite/check")
    Call<Messages> checkFavorite(@Query("idUser") String idUser);
}
