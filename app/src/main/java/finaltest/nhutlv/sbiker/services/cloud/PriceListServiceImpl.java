package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.entities.PriceList;
import finaltest.nhutlv.sbiker.services.Configuration;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import finaltest.nhutlv.sbiker.utils.Callback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by NhutDu on 17/05/2017.
 */

public class PriceListServiceImpl {

    private PriceListService mPriceListService;

    public PriceListServiceImpl(){
        mPriceListService = Configuration.getClient().create(PriceListService.class);
    }

    public void getPriceList(final Callback<PriceList> callback){
        Call<ResponseAPI<PriceList>> call = mPriceListService.getPriceList();
        call.enqueue(new retrofit2.Callback<ResponseAPI<PriceList>>() {
            @Override
            public void onResponse(Call<ResponseAPI<PriceList>> call, Response<ResponseAPI<PriceList>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<PriceList> responseAPI = response.body();
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
            public void onFailure(Call<ResponseAPI<PriceList>> call, Throwable t) {
                callback.onFailure("Đã xẩy ra lỗi");
            }
        });
    }
}
