package id.technow.reservasiklinik.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCorona { private static final String BASE_URL = "https://api.kawalcorona.com/";
    //private static final String BASE_URL = "https://psikologi.ridwan.info/api/";
    //private static final String BASE_URL = "http://10.33.35.204/ruko/public/api/";

    private static RetrofitCorona mInstance;
    private Retrofit retrofit;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private RetrofitCorona(){
    /*    HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logger)
                .build();
*/
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }


    public static synchronized RetrofitCorona getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitCorona();
        }
        return mInstance;
    }
    public ApiCorona getApi(){
        return retrofit.create(ApiCorona.class);
    }
}


