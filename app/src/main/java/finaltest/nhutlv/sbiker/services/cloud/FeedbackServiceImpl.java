package finaltest.nhutlv.sbiker.services.cloud;

import finaltest.nhutlv.sbiker.entities.Feedback;
import finaltest.nhutlv.sbiker.services.Configuration;
import finaltest.nhutlv.sbiker.services.response.ResponseAPI;
import finaltest.nhutlv.sbiker.utils.Callback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by NhutDu on 24/04/2017.
 */

public class FeedbackServiceImpl {

    public void saveFeedback(Feedback feedback, final Callback<Feedback> callback){
        FeedbackService service = Configuration.getClient().create(FeedbackService.class);
        Call<ResponseAPI<Feedback>> call = service.saveFeedback(feedback.getIdUser(),
                                                                feedback.getTitle(),feedback.getContent());

        call.enqueue(new retrofit2.Callback<ResponseAPI<Feedback>>() {
            @Override
            public void onResponse(Call<ResponseAPI<Feedback>> call, Response<ResponseAPI<Feedback>> response) {
                if(response.isSuccessful()){
                    ResponseAPI<Feedback> feedbackResponse = response.body();
                    if(!feedbackResponse.isError()){
                        callback.onResult(feedbackResponse.getData());
                    }else {
                        callback.onFailure(feedbackResponse.getMessage());
                    }
                }else{
                    callback.onFailure("Không thể kết nối máy chủ");
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<Feedback>> call, Throwable t) {
                callback.onFailure("Đã xảy ra lỗi");
            }
        });
    }

}
