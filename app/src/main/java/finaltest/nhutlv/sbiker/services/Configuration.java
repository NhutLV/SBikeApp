package finaltest.nhutlv.sbiker.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NhutDu on 21/02/2017.
 */

public class Configuration {

    private static final String BASE_URL = "http://453abc53.ngrok.io/";
    private static Retrofit mRetrofit = null;

    public static Retrofit getClient() {

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(createGson()))
                    .build();
        }
        return mRetrofit;
    }

    private static Gson createGson() {
        return new GsonBuilder().setLenient()
                .setDateFormat("yyyy-MM-dd")
                .create();
    }

}
