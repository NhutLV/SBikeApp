package finaltest.nhutlv.sbike.services.cloud;

import finaltest.nhutlv.sbike.services.response.UserResponseAPI;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by NhutDu on 07/03/2017.
 */

public interface LoginService {

    @FormUrlEncoded
    @POST("login")
    Call<UserResponseAPI> login(@Field("email") String email
            , @Field("password") String password);

}
