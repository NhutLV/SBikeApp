package finaltest.nhutlv.sbiker.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by NhutDu on 01/05/2017.
 */

public class Place implements Parcelable {

    private String mAddress;
    private String mName;
    private LatLng mLatLng;

    protected Place(Parcel in) {
        mAddress = in.readString();
        mName = in.readString();
        mLatLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public Place() {
    }

    public Place(String address, String name, LatLng latLng) {
        mAddress = address;
        mName = name;
        mLatLng = latLng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAddress);
        dest.writeString(mName);
        dest.writeParcelable(mLatLng, flags);
    }
}
