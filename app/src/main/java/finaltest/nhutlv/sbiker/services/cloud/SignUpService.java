package finaltest.nhutlv.sbiker.services.cloud;

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
    @POST("signup")
    Call<UserResponseAPI> signUp(@Field("full_name") String username,
                                 @Field("email") String email,
                                 @Field("age") int age,
                                 @Field("number_phone") String numberPhone,
                                 @Field("password") String password,
                                 @Field("latitude") double latitude,
                                 @Field("longitude") double longitude);
    @FormUrlEncoded
    @POST("sign-up-social")
    Call<UserResponseAPI> signUpSocial(@Field("full_name") String fullName,
                                       @Field("email") String email,
                                       @Field("access_token") String accessToken,
                                       @Field("image_avatar_path") String avatarPath);

}
