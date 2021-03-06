package finaltest.nhutlv.sbiker.entities;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import finaltest.nhutlv.sbiker.utils.SBFunctions;
import finaltest.nhutlv.sbiker.utils.UserLogin;

/**
 * Created by NhutDu on 18/03/2017.
 */

public class Repairer<T> {

    @SerializedName("_id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("latitude")
    private double mLatitude;

    @SerializedName("longitude")
    private double mLongitude;

    @SerializedName("number_phone")
    private String mNumberPhone;

    @SerializedName("type_repair")
    private int mType;

    @SerializedName("time_open")
    private String mTimeOpen;

    @SerializedName("time_close")
    private String mTimeClose;

    @SerializedName("id_user_created")
    private T mUserCreated;

    @SerializedName("timestamp_created")
    private String mTimestampCreate;

    private int distance;

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

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
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

    public T getUserCreated() {
        return mUserCreated;
    }

    public void setUserCreated(T userCreated) {
        mUserCreated = userCreated;
    }

    public String getTimestampCreate() {
        return mTimestampCreate;
    }

    public void setTimestampCreate(String timestampCreate) {
        mTimestampCreate = timestampCreate;
    }

    public int getDistance() {
        return distance;
    }

    public LatLng getLatLang(){
        return new LatLng(mLatitude,mLongitude);
    }

    public void setLatLng(LatLng latLng){
        mLatitude = latLng.latitude;
        mLongitude = latLng.longitude;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
