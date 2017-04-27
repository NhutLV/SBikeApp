package finaltest.nhutlv.sbiker.services.cloud;

import android.net.Uri;

import java.io.File;
import java.util.List;

import finaltest.nhutlv.sbiker.entities.Coordinate;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.Configuration;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import finaltest.nhutlv.sbiker.utils.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by NhutDu on 26/04/2017.
 */

public class UserServiceImpl {

    UserService mService;

    public UserServiceImpl(){
        mService = Configuration.getClient().create(UserService.class);
    }

    //get UserById
    public void getUserById(String id_user, final Callback<User> callback){
        Call<ResponseAPI<User>> call = mService.getUserById(id_user);
        call.enqueue(new retrofit2.Callback<ResponseAPI<User>>() {
            @Override
            public void onResponse(Call<ResponseAPI<User>> call, Response<ResponseAPI<User>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<User> responseAPI = response.body();
                    if(!responseAPI.isError()){
                        callback.onResult(responseAPI.getData());
                    }else{
                        callback.onFailure("Không thể kết nối máy chủ");
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<User>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }

    //get List user by radius
    public void getListUserByRadius(double radius, final Callback<List<User>> callback){
        Call<ResponseAPI<List<User>>> call = mService.getUserByRadius(radius);

        call.enqueue(new retrofit2.Callback<ResponseAPI<List<User>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<List<User>>> call, Response<ResponseAPI<List<User>>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<List<User>> responseAPI = response.body();
                    if(!responseAPI.isError()){
                        callback.onResult(responseAPI.getData());
                    }else{
                        callback.onFailure("Không thể kết nối máy chủ");
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<User>>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }

    //change password
    public void changePassword(String idUser, String newPassword, final Callback<User> callback){
        Call<ResponseAPI<User>> call = mService.changePassword(idUser,newPassword);
        call.enqueue(new retrofit2.Callback<ResponseAPI<User>>() {
            @Override
            public void onResponse(Call<ResponseAPI<User>> call, Response<ResponseAPI<User>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<User> responseAPI = response.body();
                    if(!responseAPI.isError()){
                        callback.onResult(responseAPI.getData());
                    }else{
                        callback.onFailure("Không thể kết nối máy chủ");
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<User>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }

    //update profile user
    public void updateProfile(User user, final Callback<User> callback){
        Call<ResponseAPI<User>> call = mService.updateProfile(user.getIdUser(),
                                                                user.getFullName(),user.getNumberPhone());
        call.enqueue(new retrofit2.Callback<ResponseAPI<User>>() {
            @Override
            public void onResponse(Call<ResponseAPI<User>> call, Response<ResponseAPI<User>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<User> responseAPI = response.body();
                    if(!responseAPI.isError()){
                        callback.onResult(responseAPI.getData());
                    }else{
                        callback.onFailure("Không thể kết nối máy chủ");
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<User>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }


    //update Coordinate user when turn on mode become driver
    public void changeCoordinate(String idUser, Coordinate coordinate, final Callback<User> callback){
        Call<ResponseAPI<User>> call = mService.updateCoordinate(idUser,coordinate.getLatitude(),coordinate.getLongitude());
        call.enqueue(new retrofit2.Callback<ResponseAPI<User>>() {
            @Override
            public void onResponse(Call<ResponseAPI<User>> call, Response<ResponseAPI<User>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<User> responseAPI = response.body();
                    if(!responseAPI.isError()){
                        callback.onResult(responseAPI.getData());
                    }else{
                        callback.onFailure("Không thể kết nối máy chủ");
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<User>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }

    //update image avatar
    public void updateImage(Uri filePath, final Callback<User> callback){
        File file = new File(filePath.getPath());

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        Call<ResponseAPI<User>> call = mService.uploadImageAvatar(body,name);
        call.enqueue(new retrofit2.Callback<ResponseAPI<User>>() {
            @Override
            public void onResponse(Call<ResponseAPI<User>> call, Response<ResponseAPI<User>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<User> responseAPI = response.body();
                    if(!responseAPI.isError()){
                        callback.onResult(responseAPI.getData());
                    }else{
                        callback.onFailure("Không thể kết nối máy chủ");
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }

            }

            @Override
            public void onFailure(Call<ResponseAPI<User>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }

    //become driver
    public void becomeDriver(String idUser, int isDriving, final Callback<User> callback){
        Call<ResponseAPI<User>> call = mService.becomeDriver(idUser,isDriving);
        call.enqueue(new retrofit2.Callback<ResponseAPI<User>>() {
            @Override
            public void onResponse(Call<ResponseAPI<User>> call, Response<ResponseAPI<User>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<User> responseAPI = response.body();
                    if(!responseAPI.isError()){
                        callback.onResult(responseAPI.getData());
                    }else{
                        callback.onFailure("Không thể kết nối máy chủ");
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<User>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }

}
