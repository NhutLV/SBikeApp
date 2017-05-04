package finaltest.nhutlv.sbiker.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by NhutDu on 24/04/2017.
 */

public class History {

    @SerializedName("id_user")
    private String mIdUser;

    @SerializedName("id_biker")
    private String mIdBiker;

    @SerializedName("time_call")
    private String mTimeCall;

    @SerializedName("place_from")
    private String mPlaceFrom;

    @SerializedName("latitude_from")
    private double mLatitudeFrom;

    @SerializedName("longitude_from")
    private double mLongitudeFrom;

    @SerializedName("place_to")
    private String mPlaceTo;

    @SerializedName("latitude_to")
    private double mLatitudeTo;

    @SerializedName("longitude_to")
    private double mLongitudeTo;

    @SerializedName("distance")
    private double mDistance;

    @SerializedName("price")
    private int mPrice;

    @SerializedName("time_spend")
    private int mTimeSpend;

    public String getIdUser() {
        return mIdUser;
    }

    public void setIdUser(String idUser) {
        mIdUser = idUser;
    }

    public String getIdBiker() {
        return mIdBiker;
    }

    public void setIdBiker(String idBiker) {
        mIdBiker = idBiker;
    }

    public String getTimeCall() {
        return mTimeCall;
    }

    public void setTimeCall(String timeCall) {
        mTimeCall = timeCall;
    }

    public String getPlaceFrom() {
        return mPlaceFrom;
    }

    public void setPlaceFrom(String placeFrom) {
        mPlaceFrom = placeFrom;
    }

    public double getLatitudeFrom() {
        return mLatitudeFrom;
    }

    public void setLatitudeFrom(double latitudeFrom) {
        mLatitudeFrom = latitudeFrom;
    }

    public double getLongitudeFrom() {
        return mLongitudeFrom;
    }

    public void setLongitudeFrom(double longitudeFrom) {
        mLongitudeFrom = longitudeFrom;
    }

    public String getPlaceTo() {
        return mPlaceTo;
    }

    public void setPlaceTo(String placeTo) {
        mPlaceTo = placeTo;
    }

    public double getLatitudeTo() {
        return mLatitudeTo;
    }

    public void setLatitudeTo(double latitudeTo) {
        mLatitudeTo = latitudeTo;
    }

    public double getLongitudeTo() {
        return mLongitudeTo;
    }

    public void setLongitudeTo(double longitudeTo) {
        mLongitudeTo = longitudeTo;
    }

    public double getDistance() {
        return mDistance;
    }

    public void setDistance(double distance) {
        mDistance = distance;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public int getTimeSpend() {
        return mTimeSpend;
    }

    public void setTimeSpend(int timeSpend) {
        mTimeSpend = timeSpend;
    }

    public History(String idUser, String idBiker, String timeCall, String placeFrom, double latitudeFrom, double longitudeFrom, String placeTo, double latitudeTo, double longitude, double distance, int price, int timeSpend) {
        mIdUser = idUser;
        mIdBiker = idBiker;
        mTimeCall = timeCall;
        mPlaceFrom = placeFrom;
        mLatitudeFrom = latitudeFrom;
        mLongitudeFrom = longitudeFrom;
        mPlaceTo = placeTo;
        mLatitudeTo = latitudeTo;
        mLongitudeTo = longitude;
        mDistance = distance;
        mPrice = price;
        mTimeSpend = timeSpend;
    }

    public History(String idBiker, String timeCall, String placeFrom, String placeTo, double distance, int price) {
        mIdBiker = idBiker;
        mTimeCall = timeCall;
        mPlaceFrom = placeFrom;
        mPlaceTo = placeTo;
        mDistance = distance;
        mPrice = price;
    }

    public History() {
    }

    @Override
    public String toString() {
        return "History{" +
                "mIdUser='" + mIdUser + '\'' +
                ", mIdBiker='" + mIdBiker + '\'' +
                ", mTimeCall=" + mTimeCall +
                ", mPlaceFrom='" + mPlaceFrom + '\'' +
                ", mLatitudeFrom=" + mLatitudeFrom +
                ", mLongitudeFrom=" + mLongitudeFrom +
                ", mPlaceTo='" + mPlaceTo + '\'' +
                ", mLatitudeTo=" + mLatitudeTo +
                ", mLongitudeTo=" + mLongitudeTo +
                ", mDistance=" + mDistance +
                ", mPrice=" + mPrice +
                ", mTimeSpend=" + mTimeSpend +
                '}';
    }
}
