package finaltest.nhutlv.sbiker.services.cloud;

import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindView;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
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

    @FormUrlEncoded
    @POST("users/radius")
    Call<ResponseAPI<List<User>>> getUserByRadius(@Field("radius") double radius,
                                                  @Field("latitude") double latitude,
                                                  @Field("longitude") double longitude);
    @FormUrlEncoded
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
    @POST("users/is-driver")
    Call<ResponseAPI<User>> isDriving(@Field("id_user") String id_user,
                                         @Field("is_driving") int is_driving);

    @FormUrlEncoded
    @POST("users/become-driver")
    Call<ResponseAPI<User>> sendBecomeInformation( @Field("id_user") String id_user,
                                                   @Field("identification_number") String identification_number,
                                                   @Field("identification_date") String identification_date,
                                                   @Field("identification_place") String identification_place,
                                                   @Field("driving_license_number") String driving_license_number,
                                                   @Field("driving_license_seri") String driving_license_seri,
                                                   @Field("number_card") String car_number_plate,
                                                   @Field("is_become") int is_become);
    @FormUrlEncoded
    @POST("user/favorite")
    Call<ResponseAPI<Boolean>> isFavorite(@Field("id_user") String id_user,
                                       @Field("id_biker") String id_biker,
                                       @Field("is_favorite") int is_favorite);

    @GET("user/favorite/{id_user}")
    Call<ResponseAPI<List<User>>> getListFavorite(@Path("id_user") String id_user);

    @GET("/user/count-favorite/{id_user}")
    Call<ResponseAPI<Integer>> getCountFavorite(@Path("id_user") String id_user);

    @GET("/user/count-not-favorite/{id_user}")
    Call<ResponseAPI<Integer>> getCountNotFavorite(@Path("id_user") String id_user);

}
