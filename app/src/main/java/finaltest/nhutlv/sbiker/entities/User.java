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

    @SerializedName("number_phone")
    private String mNumberPhone;

    @SerializedName("coordinates")
    private Coordinate mCoordinate;

    @SerializedName("access_token")
    private String mAccessToken;

    @SerializedName("image_avatar_path")
    private String mAvatarPath;

    @SerializedName("identification_card_before_path")
    private String mIdentificationCardBeforePath;

    @SerializedName("identification_card_after_path")
    private String mIdentificationCardAfterPath;

    @SerializedName("driving_license_before_path")
    private String mDrivingLicenseBeforePath;

    @SerializedName("driving_license_after_path")
    private String mDrivingLicenseAfterPath;

    @SerializedName("car_number_plate_path")
    private String mCarNumberPlatePath;

    @SerializedName("is_driving")
    private int mIsDriving;

    @SerializedName("number_favorite")
    private int mNumberFavorite;

    @SerializedName("number_not_favorite")
    private int mNumberNotFavorite;

    @SerializedName("is_approved")
    private int mIsApproved;

    @SerializedName("is_become")
    private int mIsBecome;

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

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String mAccessToken) {
        this.mAccessToken = mAccessToken;
    }

    public void setCoordinate(Coordinate coordinate) {
        mCoordinate = coordinate;
    }

    public String getAvatarPath() {
        return mAvatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        mAvatarPath = avatarPath;
    }

    public String getIdentificationCardBeforePath() {
        return mIdentificationCardBeforePath;
    }

    public void setIdentificationCardBeforePath(String identificationCardBeforePath) {
        mIdentificationCardBeforePath = identificationCardBeforePath;
    }

    public String getIdentificationCardAfterPath() {
        return mIdentificationCardAfterPath;
    }

    public void setIdentificationCardAfterPath(String identificationCardAfterPath) {
        mIdentificationCardAfterPath = identificationCardAfterPath;
    }

    public String getDrivingLicenseBeforePath() {
        return mDrivingLicenseBeforePath;
    }

    public void setDrivingLicenseBeforePath(String drivingLicenseBeforePath) {
        mDrivingLicenseBeforePath = drivingLicenseBeforePath;
    }

    public String getDrivingLicenseAfterPath() {
        return mDrivingLicenseAfterPath;
    }

    public void setDrivingLicenseAfterPath(String drivingLicenseAfterPath) {
        mDrivingLicenseAfterPath = drivingLicenseAfterPath;
    }

    public String getCarNumberPlatePath() {
        return mCarNumberPlatePath;
    }

    public void setCarNumberPlatePath(String carNumberPlatePath) {
        mCarNumberPlatePath = carNumberPlatePath;
    }

    public int getIsDriving() {
        return mIsDriving;
    }

    public void setIsDriving(int isDriving) {
        mIsDriving = isDriving;
    }

    public int getNumberFavorite() {
        return mNumberFavorite;
    }

    public void setNumberFavorite(int numberFavorite) {
        mNumberFavorite = numberFavorite;
    }

    public int getNumberNotFavorite() {
        return mNumberNotFavorite;
    }

    public void setNumberNotFavorite(int numberNotFavorite) {
        mNumberNotFavorite = numberNotFavorite;
    }

    public int getIsApproved() {
        return mIsApproved;
    }

    public void setIsApproved(int isApproved) {
        mIsApproved = isApproved;
    }

    public int getIsBecome() {
        return mIsBecome;
    }

    public void setIsBecome(int isBecome) {
        mIsBecome = isBecome;
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
