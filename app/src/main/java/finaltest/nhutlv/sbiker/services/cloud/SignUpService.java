package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import finaltest.nhutlv.sbiker.services.response.UserResponseAPI;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by NhutDu on 21/02/2017.
 */

public interface SignUpService {

    @FormUrlEncoded
    @POST("user/sign-up")
    Call<ResponseAPI<User>> signUp(@Field("full_name") String username,
                                   @Field("email") String email,
                                   @Field("number_phone") String numberPhone,
                                   @Field("password") String password);
    @FormUrlEncoded
    @POST("sign-up-social")
    Call<ResponseAPI<User>> signUpSocial(@Field("full_name") String fullName,
                                       @Field("email") String email,
                                       @Field("access_token") String accessToken,
                                       @Field("image_avatar_path") String avatarPath);

}
