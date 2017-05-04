package finaltest.nhutlv.sbiker.services.cloud;

import android.util.Log;

import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.Configuration;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NhutDu on 21/02/2017.
 */

public class SignUpServiceImpl {

    private static final String TAG = SignUpServiceImpl.class.getSimpleName();

    public void signUp(User user, final finaltest.nhutlv.sbiker.utils.Callback<User> callback){
        SignUpService service = Configuration.getClient().create(SignUpService.class);
        Call<ResponseAPI<User>> call = service.signUp(user.getFullName(),user.getEmai(),
                user.getNumberPhone(),user.getPassword());
        call.enqueue(new Callback<ResponseAPI<User>>() {
            @Override
            public void onResponse(Call<ResponseAPI<User>> call, Response<ResponseAPI<User>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<User> responseAPI = response.body();
                    if(!responseAPI.isError()){
                        callback.onResult(responseAPI.getData());
                    }else{
                        callback.onFailure(responseAPI.getMessage());
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<User>> call, Throwable t) {
                callback.onFailure("Xảy ra lỗi trong quá trình đăng ký");
            }
        });
    }

    public void signUpSocial(User user, final finaltest.nhutlv.sbiker.utils.Callback<User> callback){
        SignUpService service = Configuration.getClient().create(SignUpService.class);
        Call<ResponseAPI<User>> call = service.signUpSocial(user.getFullName(),
                                        user.getEmai(),user.getAccessToken(),user.getAvatarPath());

        call.enqueue(new Callback<ResponseAPI<User>>() {
            @Override
            public void onResponse(Call<ResponseAPI<User>> call, Response<ResponseAPI<User>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<User> responseAPI = response.body();
                    if(!responseAPI.isError()){
                        callback.onResult(responseAPI.getData());
                    }else{
                        callback.onFailure(responseAPI.getMessage());
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<User>> call, Throwable t) {
                callback.onFailure("Xảy ra lỗi trong quá trình đăng ký");
            }
        });
    }

}
