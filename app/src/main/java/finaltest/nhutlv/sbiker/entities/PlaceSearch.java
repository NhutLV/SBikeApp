package finaltest.nhutlv.sbiker.entities;

/**
 * Created by NhutDu on 30/04/2017.
 */

public class PlaceSearch {

    private Geometry geometry;
    private String name;
    private String vicinity;
    private double distance;

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
