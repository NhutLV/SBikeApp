package finaltest.nhutlv.sbike.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NhutDu on 28/02/2017.
 */

public class Coordinate {

    @SerializedName("latitude")
    private double mLatitude;

    @SerializedName("longitude")
    private double mLongitude;

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public Coordinate(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public Coordinate() {
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }
}
