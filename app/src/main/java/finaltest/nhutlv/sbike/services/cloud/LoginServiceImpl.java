package finaltest.nhutlv.sbike.services.cloud;

import android.util.Log;

import finaltest.nhutlv.sbike.entities.User;
import finaltest.nhutlv.sbike.services.Configuration;
import finaltest.nhutlv.sbike.services.response.UserResponseAPI;
import finaltest.nhutlv.sbike.utils.Callback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by NhutDu on 07/03/2017.
 */

public class LoginServiceImpl {

    private static final String TAG = LoginServiceImpl.class.getSimpleName();

    public void login(String email, String password, final Callback<User> callback){

        LoginService service = Configuration.getClient().create(LoginService.class);
        Call<UserResponseAPI> call = service.login(email,password);
        call.enqueue(new retrofit2.Callback<UserResponseAPI>() {
            @Override
            public void onResponse(Call<UserResponseAPI> call, Response<UserResponseAPI> response) {
                UserResponseAPI responseAPI = response.body();
                Log.d(TAG, "Error: "+responseAPI.isError());
                Log.d(TAG, "Message: "+responseAPI.getMessage());
                if(responseAPI.isError()){
                    callback.onResult(null);
                }else{
                    callback.onResult(responseAPI.getUser());
                }
            }

            @Override
            public void onFailure(Call<UserResponseAPI> call, Throwable t) {
                Log.d(TAG, "onFailure: ",t);
                callback.onResult(null);
            }
        });


    }

}
