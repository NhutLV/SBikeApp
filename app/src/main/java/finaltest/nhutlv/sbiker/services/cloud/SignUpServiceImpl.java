package finaltest.nhutlv.sbiker.services.cloud;

import android.util.Log;

import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.Configuration;
import finaltest.nhutlv.sbiker.services.response.UserResponseAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NhutDu on 21/02/2017.
 */

public class SignUpServiceImpl {

    private static final String TAG = SignUpServiceImpl.class.getSimpleName();

    public void registerUser(User user, final finaltest.nhutlv.sbiker.utils.Callback<Boolean> callback){
        SignUpService service = Configuration.getClient().create(SignUpService.class);
        Call<UserResponseAPI> call = service.signUp(user.getFullName(),user.getEmai(),user.getAge(),
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

    public void signUpSocial(User user, final finaltest.nhutlv.sbiker.utils.Callback<User> callback){
        SignUpService service = Configuration.getClient().create(SignUpService.class);
        Call<UserResponseAPI> call = service.signUpSocial(user.getFullName(),
                                        user.getEmai(),user.getAccessToken(),user.getAvatarPath());

        call.enqueue(new Callback<UserResponseAPI>() {
            @Override
            public void onResponse(Call<UserResponseAPI> call, Response<UserResponseAPI> response) {
                if(response.isSuccessful()){
                    UserResponseAPI responseAPI = response.body();
                    if(!responseAPI.isError()){
                        callback.onResult(responseAPI.getUser());
                    }else{
                        callback.onFailure("Xảy ra lỗi trong quá trình đăng ký");
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<UserResponseAPI> call, Throwable t) {
                callback.onFailure("");
            }
        });
    }

}
