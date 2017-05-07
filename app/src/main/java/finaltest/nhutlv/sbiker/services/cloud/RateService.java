package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.entities.Rating;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by NhutDu on 07/05/2017.
 */

public interface RateService {

    @FormUrlEncoded
    @POST("rate")
    Call<ResponseAPI<Rating>> sendRating(@Field("id_history") String id_history,
                                         @Field("rating") String rating,
                                         @Field("comment") String comment);
}
