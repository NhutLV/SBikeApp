package finaltest.nhutlv.sbiker.services.cloud;

import java.util.ArrayList;

import finaltest.nhutlv.sbiker.entities.Repairer;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.Configuration;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import finaltest.nhutlv.sbiker.utils.Callback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by NhutDu on 07/05/2017.
 */

public class RepairServiceImpl {

    private RepairService mRepairService;

    public RepairServiceImpl(){
        mRepairService = Configuration.getClient().create(RepairService.class);
    }

    //save repair
    public void saveRepair(final Repairer<String> repairer, final Callback<Repairer<String>> callback){
        Call<ResponseAPI<Repairer<String>>> call = mRepairService.saveRepair(repairer.getName(),repairer.getAddress(),
                repairer.getLatitude(), repairer.getLongitude(),repairer.getNumberPhone(),repairer.getTimeOpen(),
                repairer.getTimeClose(),repairer.getType(), repairer.getUserCreated());
        call.enqueue(new retrofit2.Callback<ResponseAPI<Repairer<String>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<Repairer<String>>> call, Response<ResponseAPI<Repairer<String>>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<Repairer<String>> responseAPI = response.body();
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
            public void onFailure(Call<ResponseAPI<Repairer<String>>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }

    //get list repair
    public void getListRepairByType(int type, final Callback<ArrayList<Repairer<User>>> callback){
        Call<ResponseAPI<ArrayList<Repairer<User>>>> call = mRepairService.getRepairByType(type);
        call.enqueue(new retrofit2.Callback<ResponseAPI<ArrayList<Repairer<User>>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<ArrayList<Repairer<User>>>> call, Response<ResponseAPI<ArrayList<Repairer<User>>>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<ArrayList<Repairer<User>>> responseAPI = response.body();
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
            public void onFailure(Call<ResponseAPI<ArrayList<Repairer<User>>>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }
}
