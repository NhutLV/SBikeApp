package finaltest.nhutlv.sbiker.entities;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NhutDu on 28/02/2017.
 */

public class User {

    @SerializedName("email")
    private String mEmai;

    @SerializedName("password")
    private String mPassword;

    @SerializedName("fullName")
    private String mFullName;

    @SerializedName("age")
    private int mAge;

    @SerializedName("numberPhone")
    private String mNumberPhone;

    @SerializedName("coordinates")
    private Coordinate mCoordinate;

    private String mRePassword;


    public String getEmai() {
        return mEmai;
    }

    public void setEmai(String emai) {
        mEmai = emai;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getNumberPhone() {
        return mNumberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        mNumberPhone = numberPhone;
    }

    public Coordinate getCoordinate() {
        return mCoordinate;
    }


    public void setCoordinate(Coordinate coordinate) {
        mCoordinate = coordinate;
    }

    public User(String emai, String password, String fullName, int age, String numberPhone, Coordinate coordinate) {
        mEmai = emai;
        mPassword = password;
        mFullName = fullName;
        mAge = age;
        mNumberPhone = numberPhone;
        mCoordinate = coordinate;
    }

    public User() {
    }

    public User(String mFullName, Coordinate mCoordinate) {
        this.mFullName = mFullName;
        this.mCoordinate = mCoordinate;
    }

    public User(String mFullName, String mNumberPhone, Coordinate mCoordinate) {
        this.mFullName = mFullName;
        this.mNumberPhone = mNumberPhone;
        this.mCoordinate = mCoordinate;
    }

    public String getRePassword() {
        return mRePassword;
    }

    public void setRePassword(String rePassword) {
        mRePassword = rePassword;
    }
}
