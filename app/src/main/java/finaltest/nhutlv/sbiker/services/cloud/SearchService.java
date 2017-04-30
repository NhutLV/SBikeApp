package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.services.response.SearchResponseAPI;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NhutDu on 30/04/2017.
 */

public interface SearchService {

    @GET("json?sensor=true&key=AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0&language=vi")
    Call<SearchResponseAPI> getPlaceSearch(@Query("location") String location,
                                           @Query("type") String type,
                                           @Query("radius") int radius);

}
