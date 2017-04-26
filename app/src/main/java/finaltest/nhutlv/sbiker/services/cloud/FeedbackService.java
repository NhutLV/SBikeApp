package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.entities.Feedback;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by NhutDu on 24/04/2017.
 */

public interface FeedbackService {

    @FormUrlEncoded
    @POST("feedback")
    Call<ResponseAPI<Feedback>> saveFeedback(@Field("id_user") String idUser,
                                             @Field("title") String title,
                                             @Field("content") String content);

}
