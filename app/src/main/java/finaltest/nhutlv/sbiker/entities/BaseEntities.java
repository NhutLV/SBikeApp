package finaltest.nhutlv.sbiker.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NhutDu on 18/03/2017.
 */

public abstract class BaseEntities {

    @SerializedName("_id")
    private String mId;

    @SerializedName("name")
    private String mFullName;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("numberphone")
    private String mNumberPhone;

    @SerializedName("type")
    private String mType;

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

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
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

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public BaseEntities(String id, String fullName, String address, String numberPhone, String type) {
        mId = id;
        mFullName = fullName;
        mAddress = address;
        mNumberPhone = numberPhone;
        mType = type;
    }

    public BaseEntities(String fullName, String address, String numberPhone, String type) {
        mFullName = fullName;
        mAddress = address;
        mNumberPhone = numberPhone;
        mType = type;
    }

    public BaseEntities() {
    }
}
