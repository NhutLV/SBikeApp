package finaltest.nhutlv.sbiker.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NhutDu on 24/04/2017.
 */

public class Feedback {

    @SerializedName("id_user")
    private String mIdUser;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("content")
    private String mContent;

    public String getIdUser() {
        return mIdUser;
    }

    public void setIdUser(String idUser) {
        mIdUser = idUser;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Feedback() {
    }

    public Feedback(String idUser, String title, String content) {
        mIdUser = idUser;
        mTitle = title;
        mContent = content;
    }
}
