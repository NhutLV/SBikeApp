package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.services.response.UserResponseAPI;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by NhutDu on 21/02/2017.
 */

public interface RegisterService {

    @FormUrlEncoded
    @POST("signup")
    Call<UserResponseAPI> register(@Field("fullName") String username,
                                   @Field("email") String email,
                                   @Field("age") int age,
                                   @Field("numberPhone") String numberPhone,
                                   @Field("password") String password,
                                   @Field("latitude") double latitude,
                                   @Field("longitude") double longitude);

}
