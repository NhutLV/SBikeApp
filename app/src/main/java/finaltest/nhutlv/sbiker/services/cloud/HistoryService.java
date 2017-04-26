package finaltest.nhutlv.sbiker.services.cloud;

import java.util.Date;
import java.util.List;

import finaltest.nhutlv.sbiker.entities.History;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by NhutDu on 24/04/2017.
 */

public interface HistoryService {

    @FormUrlEncoded
    @POST("")
    Call<ResponseAPI<History>> saveHistory(@Field("id_user") String idUser,
                                           @Field("id_biker") String idBiker,
                                           @Field("time_call") String timeCall,
                                           @Field("place_from") String placeFrom,
                                           @Field("latitude_from") double latitudeFrom,
                                           @Field("longitude_from") double longitudeFrom,
                                           @Field("place_from") String placeTo,
                                           @Field("latitude_to") double latitudeTo,
                                           @Field("longitude_to") double longitudeTo,
                                           @Field("distance") double distance,
                                           @Field("price") int price,
                                           @Field("time_spend") int timeSpend);

    @GET("")
    Call<ResponseAPI<List<History>>> getHistory(@Field("id_user") String idUser);
}
