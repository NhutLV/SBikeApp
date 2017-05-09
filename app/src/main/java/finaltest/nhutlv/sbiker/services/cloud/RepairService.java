package finaltest.nhutlv.sbiker.services.cloud;

import java.util.ArrayList;

import finaltest.nhutlv.sbiker.entities.Repairer;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by NhutDu on 07/05/2017.
 */

public interface RepairService {

    @FormUrlEncoded
    @POST("repair/create")
    Call<ResponseAPI<Repairer<String>>> saveRepair(@Field("name") String name,
                                                        @Field("address") String address,
                                                        @Field("latitude") double latitude,
                                                        @Field("longitude") double longitude,
                                                        @Field("number_phone") String numberPhone,
                                                        @Field("time_open") String timeOpen,
                                                        @Field("time_close" ) String timeClose ,
                                                        @Field("type_repair") int type,
                                                        @Field("id_user_created") String id_user_created);

    @GET("repair/{type_repair}")
    Call<ResponseAPI<ArrayList<Repairer<User>>>> getRepairByType(@Path("type_repair") int type);


}
