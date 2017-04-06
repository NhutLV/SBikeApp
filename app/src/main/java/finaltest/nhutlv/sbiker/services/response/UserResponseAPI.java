package finaltest.nhutlv.sbiker.services.response;

import com.google.gson.annotations.SerializedName;

import finaltest.nhutlv.sbiker.entities.User;

/**
 * Created by NhutDu on 21/02/2017.
 */

public class UserResponseAPI {

    @SerializedName("user")
    private User mUser;

    @SerializedName("error")
    private boolean mError;

    @SerializedName("message")
    private String mMessage;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
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

    public UserResponseAPI(User user, boolean error, String message) {
        mUser = user;
        mError = error;
        mMessage = message;
    }

    public UserResponseAPI() {
    }
}
