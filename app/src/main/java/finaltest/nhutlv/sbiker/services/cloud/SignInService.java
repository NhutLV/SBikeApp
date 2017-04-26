package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.services.response.UserResponseAPI;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by NhutDu on 07/03/2017.
 */

public interface SignInService {

    @FormUrlEncoded
    @POST("activity_login")
    Call<UserResponseAPI> login(@Field("email") String email
            , @Field("password") String password);

    @FormUrlEncoded
    @POST("user/sign-in-social")
    Call<UserResponseAPI> signSocial(@Field("access_token") String accessToken);

}
