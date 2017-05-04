package finaltest.nhutlv.sbiker.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NhutDu on 30/04/2017.
 */

public class PlaceSearch implements Parcelable{

    private Geometry geometry;
    private String name;
    private String vicinity;
    private double distance;

    protected PlaceSearch(Parcel in) {
        name = in.readString();
        vicinity = in.readString();
        distance = in.readDouble();
    }

    public static final Creator<PlaceSearch> CREATOR = new Creator<PlaceSearch>() {
        @Override
        public PlaceSearch createFromParcel(Parcel in) {
            return new PlaceSearch(in);
        }

        @Override
        public PlaceSearch[] newArray(int size) {
            return new PlaceSearch[size];
        }
    };

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(vicinity);
        dest.writeDouble(distance);
    }

    public class Geometry{
        
        private Location location;

        public Geometry(Location location) {
            location = location;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            location = location;
        }
    }
}
