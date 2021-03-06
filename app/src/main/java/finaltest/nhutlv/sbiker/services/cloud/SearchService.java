package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.services.response.SearchResponseAPI;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NhutDu on 30/04/2017.
 */

public interface SearchService {

    @GET("json?sensor=true&key=AIzaSyA_Brr_ZL4c7ksiENhJ9ak80WAtgpk2WyQ&language=vi")
    Call<SearchResponseAPI> getPlaceSearch(@Query("location") String location,
                                           @Query("type") String type,
                                           @Query("radius") int radius);

}
