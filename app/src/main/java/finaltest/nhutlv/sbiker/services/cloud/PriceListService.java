package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.entities.PriceList;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by NhutDu on 17/05/2017.
 */

public interface PriceListService {

    @GET("pricelist")
    Call<ResponseAPI<PriceList>> getPriceList();
}
