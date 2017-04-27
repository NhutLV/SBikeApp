package finaltest.nhutlv.sbiker.services.cloud;

import java.util.List;

import butterknife.BindView;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by NhutDu on 26/04/2017.
 */

public interface UserService {

    @GET("users/{id_user}")
    Call<ResponseAPI<User>> getUserById(@Path("id_user") String id_user);

    @GET("users/{radius}")
    Call<ResponseAPI<List<User>>> getUserByRadius(@Path("radius") double radius);

    @POST("user/change-pass")
    Call<ResponseAPI<User>> changePassword(@Field("id_user") String id_user,
                                           @Field("new_pass") String new_pass);

    @POST("user/update-profile")
    Call<ResponseAPI<User>> updateProfile(@Field("id_user") String id_user,
                                          @Field("full_name") String full_name,
                                          @Field("number_phone") String numberPhone);

    @Multipart
    @POST("user/image-")
    Call<ResponseAPI<User>> uploadImageAvatar(@Part MultipartBody.Part image,
                                              @Part("name") RequestBody name);

    @FormUrlEncoded
    @POST("users/update-coordinate")
    Call<ResponseAPI<User>> updateCoordinate(@Field("id_user") String id_user,
                                             @Field("latitude") double latitude,
                                             @Field("longitude") double longitude);

    @FormUrlEncoded
    @POST("user/become-driver")
    Call<ResponseAPI<User>> becomeDriver(@Field("id_user") String id_user,
                                         @Field("is_driving") int is_driving);


}
