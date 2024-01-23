package md06.fpoly.gentlewear.classs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//create by namtd
public class RetrofitClientAPI {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIClass.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}