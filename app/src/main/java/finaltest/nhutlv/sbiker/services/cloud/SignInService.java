package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
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
    @POST("user/sign-in")
    Call<ResponseAPI<User>> login(@Field("email") String email
                                , @Field("password") String password);

    @FormUrlEncoded
    @POST("users/sign-in-social")
    Call<ResponseAPI<User>> signSocial(@Field("access_token") String accessToken);

}
