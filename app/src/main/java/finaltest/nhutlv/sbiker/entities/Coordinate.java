package finaltest.nhutlv.sbiker.entities;

import com.google.android.gms.maps.model.LatLng;
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

    public LatLng getLatLng(){
        return new LatLng(mLatitude,mLongitude);
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
