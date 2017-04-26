package finaltest.nhutlv.sbiker.services.cloud;

import android.content.Context;
import android.util.Log;

import finaltest.nhutlv.sbiker.activities.SignInActivity;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.Configuration;
import finaltest.nhutlv.sbiker.services.response.UserResponseAPI;
import finaltest.nhutlv.sbiker.tools.ErrorDialog;
import finaltest.nhutlv.sbiker.utils.Callback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by NhutDu on 07/03/2017.
 */

public class SignInServiceImpl {

    Context mContext;

    public SignInServiceImpl() {
    }

    public SignInServiceImpl(Context mContext) {
        this.mContext = mContext;
    }

    private static final String TAG = SignInServiceImpl.class.getSimpleName();

    public void login(String email, String password, final Callback<User> callback){

        SignInService service = Configuration.getClient().create(SignInService.class);
        Call<UserResponseAPI> call = service.login(email,password);
        call.enqueue(new retrofit2.Callback<UserResponseAPI>() {
            @Override
            public void onResponse(Call<UserResponseAPI> call, Response<UserResponseAPI> response) {
                if(response.isSuccessful()){
                    UserResponseAPI responseAPI = response.body();
                    Log.d(TAG, "Error: "+responseAPI.isError());
                    Log.d(TAG, "Message: "+responseAPI.getMessage());
                    if(responseAPI.isError()){
                        callback.onResult(null);
                    }else{
                        callback.onFailure("Không thể kết nối máy chủ");
                    }
                }else{
                    callback.onFailure("Đã có lỗi xảy ra trong quá trình đăng nhập");
                }
            }

            @Override
            public void onFailure(Call<UserResponseAPI> call, Throwable t) {
                Log.d(TAG, "onFailure: ",t);
                callback.onFailure("Không thể kết nối mạng");
            }
        });
    }

    public void signInSocial(String accessToken, final Callback<User> callback){
        SignInService service = Configuration.getClient().create(SignInService.class);
        Call<UserResponseAPI> call = service.signSocial(accessToken);
        call.enqueue(new retrofit2.Callback<UserResponseAPI>() {
            @Override
            public void onResponse(Call<UserResponseAPI> call, Response<UserResponseAPI> response) {
                if(response.isSuccessful()){
                    UserResponseAPI responseAPI = response.body();
                    if(!responseAPI.isError()){
                        Log.d(TAG, "onResponse: "+responseAPI.getUser().toString());
                        callback.onResult(responseAPI.getUser());
                    }else{
                        callback.onResult(null);
                        Log.d(TAG, "onResponse: Error");
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<UserResponseAPI> call, Throwable t) {
                callback.onFailure("Không thể kết nối mạng");
                Log.d(TAG, "onFailure: ",t);
            }
        });
    }
}
