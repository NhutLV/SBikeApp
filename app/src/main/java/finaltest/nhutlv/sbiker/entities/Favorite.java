package finaltest.nhutlv.sbiker.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by NhutDu on 05/05/2017.
 */

public class Favorite {

    @SerializedName("_id")
    private String mId;

    @SerializedName("id_user")
    private String mIdUser;

    @SerializedName("is_favorite")
    private int mIsFavorite;

    @SerializedName("id_biker")
    private User mBikers;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getIdUser() {
        return mIdUser;
    }

    public void setIdUser(String idUser) {
        mIdUser = idUser;
    }

    public int getIsFavorite() {
        return mIsFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        mIsFavorite = isFavorite;
    }

    public User getBikers() {
        return mBikers;
    }

    public void setBikers(User bikers) {
        mBikers = bikers;
    }
}
