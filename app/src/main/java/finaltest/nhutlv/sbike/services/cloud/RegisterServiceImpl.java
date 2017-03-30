package finaltest.nhutlv.sbike.services.cloud;

import android.util.Log;

import finaltest.nhutlv.sbike.entities.User;
import finaltest.nhutlv.sbike.services.Configuration;
import finaltest.nhutlv.sbike.services.response.UserResponseAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NhutDu on 21/02/2017.
 */

public class RegisterServiceImpl {

    private static final String TAG = RegisterServiceImpl.class.getSimpleName();

    public void registerUser(User user, final finaltest.nhutlv.sbike.utils.Callback<Boolean> callback){
        RegisterService service = Configuration.getClient().create(RegisterService.class);
        Call<UserResponseAPI> call = service.register(user.getFullName(),user.getEmai(),user.getAge(),
                user.getNumberPhone(),user.getPassword(),user.getCoordinate().getLatitude(),user.getCoordinate().getLongitude());
        call.enqueue(new Callback<UserResponseAPI>() {
            @Override
            public void onResponse(Call<UserResponseAPI> call, Response<UserResponseAPI> response) {

                callback.onResult(true);
                Log.d(TAG, "onResponse: Result "+response.body().isError());
//                if(response.body().isResult()){
//                    Log.d(TAG, "onResponse: OK");
//                    callback.onResult(true);
//                }else{
//                    Log.d(TAG, "onResponse: Failed");
//                    callback.onResult(false);
//                }
            }

            @Override
            public void onFailure(Call<UserResponseAPI> call, Throwable t) {
                Log.d(TAG, "onFailure: ",t);
                callback.onResult(false);
            }
        });

    }

}
