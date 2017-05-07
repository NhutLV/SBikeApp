package finaltest.nhutlv.sbiker.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NhutDu on 18/03/2017.
 */

public abstract class BaseEntities {

    @SerializedName("_id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("number_phone")
    private String mNumberPhone;

    @SerializedName("type_repair")
    private int mType;

    @SerializedName("time_open")
    private String mTimeOpen;

    @SerializedName("time_close")
    private String mTimeClose;

    @SerializedName("id_user_created")
    private User mUserCreated;

    @SerializedName("timestamp_created")
    private String mTimestampCreate;

    public Coordinate getCoordinate() {
        return mCoordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        mCoordinate = coordinate;
    }

    private Coordinate mCoordinate;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getNumberPhone() {
        return mNumberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        mNumberPhone = numberPhone;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getTimeOpen() {
        return mTimeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        mTimeOpen = timeOpen;
    }

    public String getTimeClose() {
        return mTimeClose;
    }

    public void setTimeClose(String timeClose) {
        mTimeClose = timeClose;
    }

    public User getUserCreated() {
        return mUserCreated;
    }

    public void setUserCreated(User userCreated) {
        mUserCreated = userCreated;
    }

    public String getTimestampCreate() {
        return mTimestampCreate;
    }

    public void setTimestampCreate(String timestampCreate) {
        mTimestampCreate = timestampCreate;
    }

    public BaseEntities(String id, String fullName, String address, String numberPhone, int type) {
        mId = id;
        mName = fullName;
        mAddress = address;
        mNumberPhone = numberPhone;
        mType = type;
    }

    public BaseEntities(String fullName, String address, String numberPhone, int type) {
        mName = fullName;
        mAddress = address;
        mNumberPhone = numberPhone;
        mType = type;
    }

    public BaseEntities() {
    }
}
