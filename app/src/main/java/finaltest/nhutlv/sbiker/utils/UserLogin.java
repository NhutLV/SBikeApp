package finaltest.nhutlv.sbiker.utils;

import com.google.android.gms.common.api.GoogleApiClient;

import finaltest.nhutlv.sbiker.entities.User;

/**
 * Created by NhutDu on 18/04/2017.
 */

public class UserLogin {

    public static User mUserLogin;
    public static GoogleApiClient mGoogleApiClient;

    public static User getUserLogin() {
        return mUserLogin;
    }

    public static void setUserLogin(User userLogin) {
        mUserLogin = userLogin;
    }

    public static GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public static void setGoogleApiClient(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
    }

    public UserLogin() {
    }
}
