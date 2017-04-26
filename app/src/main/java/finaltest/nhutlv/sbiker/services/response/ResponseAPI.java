package finaltest.nhutlv.sbiker.services.response;

import com.google.gson.annotations.SerializedName;

import finaltest.nhutlv.sbiker.entities.User;

/**
 * Created by NhutDu on 24/04/2017.
 */

public class ResponseAPI<T> {
    @SerializedName("data")
    private T mData;

    @SerializedName("error")
    private boolean mError;

    @SerializedName("message")
    private String mMessage;

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public boolean isError() {
        return mError;
    }

    public void setError(boolean error) {
        mError = error;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public ResponseAPI() {
    }

    public ResponseAPI(T data, boolean error, String message) {
        mData = data;
        mError = error;
        mMessage = message;
    }
}
