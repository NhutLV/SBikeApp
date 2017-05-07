package finaltest.nhutlv.sbiker.services.cloud;

import android.util.Log;

import java.util.List;

import finaltest.nhutlv.sbiker.entities.History;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.Configuration;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import finaltest.nhutlv.sbiker.utils.Callback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by NhutDu on 24/04/2017.
 */

public class HistoryServiceImpl {

    public void saveHistory(final History<String> history, final Callback<History<String>> callback){
        HistoryService service = Configuration.getClient().create(HistoryService.class);
        Call<ResponseAPI<History<String>>> call = service.saveHistory(history.getIdUser(),
                (String) history.getBiker(),history.getTimeCall().toString(),
                history.getPlaceFrom(),history.getLatitudeFrom(), history.getLongitudeFrom(),
                history.getPlaceTo(), history.getLatitudeTo(),history.getLongitudeTo(),
                history.getDistance(),history.getPrice(),history.getTimeSpend());

        call.enqueue(new retrofit2.Callback<ResponseAPI<History<String>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<History<String>>> call, Response<ResponseAPI<History<String>>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<History<String>> historyResponse = response.body();
                    Log.d("TAGGGGGGG",historyResponse.getMessage());
                    Log.d("TAGGGGGGG",historyResponse.isError()+"");
                    if(!historyResponse.isError()){
                        callback.onResult(historyResponse.getData());
                    }else{
                        callback.onFailure(historyResponse.getMessage());
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<History<String>>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }

    public void getListHistory(String idUser, final Callback<List<History<User>>> callback){
        HistoryService service = Configuration.getClient().create(HistoryService.class);
        Call<ResponseAPI<List<History<User>>>> call = service.getHistory(idUser);
        call.enqueue(new retrofit2.Callback<ResponseAPI<List<History<User>>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<List<History<User>>>> call, Response<ResponseAPI<List<History<User>>>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<List<History<User>>> historiesResponse = response.body();
                    if(!historiesResponse.isError()){
                        callback.onResult(historiesResponse.getData());
                    }else{
                        callback.onFailure(historiesResponse.getMessage());
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<History<User>>>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }
}
