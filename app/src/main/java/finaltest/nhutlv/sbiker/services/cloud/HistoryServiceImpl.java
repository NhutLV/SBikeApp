package finaltest.nhutlv.sbiker.services.cloud;

import java.util.List;

import finaltest.nhutlv.sbiker.entities.History;
import finaltest.nhutlv.sbiker.services.Configuration;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import finaltest.nhutlv.sbiker.utils.Callback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by NhutDu on 24/04/2017.
 */

public class HistoryServiceImpl {

    public void saveHistory(final History history, final Callback<History> callback){
        HistoryService service = Configuration.getClient().create(HistoryService.class);
        Call<ResponseAPI<History>> call = service.saveHistory(history.getIdUser(),
                history.getIdBiker(),history.getTimeCall().toString(),
                history.getPlaceFrom(),history.getLatitudeFrom(), history.getLongitudeFrom(),
                history.getPlaceTo(), history.getLatitudeTo(),history.getLongitudeTo(),
                history.getDistance(),history.getPrice(),history.getTimeSpend());

        call.enqueue(new retrofit2.Callback<ResponseAPI<History>>() {
            @Override
            public void onResponse(Call<ResponseAPI<History>> call, Response<ResponseAPI<History>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<History> historyResponse = response.body();
                    if(!historyResponse.isError()){
                        callback.onResult(historyResponse.getData());
                    }else{
                        callback.onFailure("Không thể kết nối máy chủ");
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<History>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }

    public void getListHistory(String idUser, final Callback<List<History>> callback){
        HistoryService service = Configuration.getClient().create(HistoryService.class);
        Call<ResponseAPI<List<History>>> call = service.getHistory(idUser);
        call.enqueue(new retrofit2.Callback<ResponseAPI<List<History>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<List<History>>> call, Response<ResponseAPI<List<History>>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<List<History>> historiesResponse = response.body();
                    if(!historiesResponse.isError()){
                        callback.onResult(historiesResponse.getData());
                    }else{
                        callback.onFailure("Không thể kết nối máy chủ");
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<History>>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }
}
