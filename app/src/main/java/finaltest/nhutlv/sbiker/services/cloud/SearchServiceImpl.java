package finaltest.nhutlv.sbiker.services.cloud;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import finaltest.nhutlv.sbiker.entities.PlaceSearch;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import finaltest.nhutlv.sbiker.services.response.SearchResponseAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NhutDu on 30/04/2017.
 */

public class SearchServiceImpl {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    private static Retrofit mRetrofit = null;

    public void getListPlaceSearch(LatLng latLng, String type, int radius, final finaltest.nhutlv.sbiker.utils.Callback<List<PlaceSearch>> callback){
        SearchService service = getClient().create(SearchService.class);
        String location = latLng.latitude+","+latLng.longitude;
        Call<SearchResponseAPI> call = service.getPlaceSearch(location,type,radius);
        call.enqueue(new Callback<SearchResponseAPI>() {
            @Override
            public void onResponse(Call<SearchResponseAPI> call, Response<SearchResponseAPI> response) {
                if(response.isSuccessful()){
                    Log.d("TAG", "onResponse: success");
                    SearchResponseAPI responseAPI = response.body();
                    if(responseAPI.getStatus().equals("OK")){
                        Log.d("TAG", "onResponse: OK");
                        Log.d("TAG", "onResponse: "+responseAPI.getResults().size());
                        callback.onResult(responseAPI.getResults());
                    }else{
                        Log.d("TAG", "onResponse: NOT OK");
                        callback.onResult(null);
                    }
                }else{
                    Log.d("TAG", "onResponse: not success");
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<SearchResponseAPI> call, Throwable t) {
                Log.d("TAG", "onResponse: Fail" +
                        "");
                callback.onFailure("Đã xảy ra lỗi");
            }
        });

    }

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
