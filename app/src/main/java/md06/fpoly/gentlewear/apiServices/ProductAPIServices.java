package md06.fpoly.gentlewear.apiServices;

import md06.fpoly.gentlewear.models.ResProduct;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductAPIServices {
    @GET("api/products")
    Call<ResProduct> getProduct(@Query("page") int page, @Query("pageSize") int pageSize);
    @GET("api/products/search")
    Call<ResProduct> getResultSearch(@Query("keyword") String keyword,@Query("page") int page, @Query("pageSize") int pageSize);
    @GET("api/products/filter")
    Call<ResProduct> getProductWithFilter(@Query("page") int page, @Query("pageSize") int pageSize);//chua hoan thanh api 13/03
}
