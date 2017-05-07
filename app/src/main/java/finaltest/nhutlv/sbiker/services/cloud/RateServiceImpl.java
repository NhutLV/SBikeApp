package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.entities.Rating;
import finaltest.nhutlv.sbiker.services.Configuration;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import finaltest.nhutlv.sbiker.utils.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by NhutDu on 07/05/2017.
 */

public class RateServiceImpl {

    private RateService mService;

    public RateServiceImpl(){
        mService = Configuration.getClient().create(RateService.class);
    }

    public void sendRate(String idHistory, String rating, String comment, final Callback<Rating> callback){
        Call<ResponseAPI<Rating>> call = mService.sendRating(idHistory,rating, comment);
        call.enqueue(new retrofit2.Callback<ResponseAPI<Rating>>() {
            @Override
            public void onResponse(Call<ResponseAPI<Rating>> call, retrofit2.Response<ResponseAPI<Rating>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<Rating> responseAPI = response.body();
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
            public void onFailure(Call<ResponseAPI<Rating>> call, Throwable t) {
                callback.onFailure("Đã có lỗi xảy ra");
            }
        });
    }

}
