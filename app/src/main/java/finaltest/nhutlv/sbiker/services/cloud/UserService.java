package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by NhutDu on 26/04/2017.
 */

public interface UserService {

    @GET("users/{id_user}")
    Call<ResponseAPI<User>> getUserById(@Path("id_user") String id_user);


}
