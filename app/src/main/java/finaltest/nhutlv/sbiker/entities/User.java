package finaltest.nhutlv.sbiker.entities;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NhutDu on 28/02/2017.
 */

public class User {

    @SerializedName("_id")
    private String mIdUser;

    @SerializedName("email")
    private String mEmai;

    @SerializedName("password")
    private String mPassword;

    @SerializedName("fullname")
    private String mFullName;

    @SerializedName("age")
    private int mAge;

    @SerializedName("number_phone")
    private String mNumberPhone;

    @SerializedName("latitude")
    private double mLatitude;

    @SerializedName("longitude")
    private double mLongitude;

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

    public String getIdUser() {
        return mIdUser;
    }

    public void setIdUser(String idUser) {
        mIdUser = idUser;
    }

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


    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String mAccessToken) {
        this.mAccessToken = mAccessToken;
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

    public User(String emai, String password, String fullName, int age, String numberPhone) {
        mEmai = emai;
        mPassword = password;
        mFullName = fullName;
        mAge = age;
        mNumberPhone = numberPhone;
    }

    public User() {
    }

    public User(String mFullName, Coordinate mCoordinate) {
        this.mFullName = mFullName;
    }

    public User(String mFullName, String mNumberPhone, Coordinate mCoordinate) {
        this.mFullName = mFullName;
        this.mNumberPhone = mNumberPhone;
    }

    public String getRePassword() {
        return mRePassword;
    }

    public void setRePassword(String rePassword) {
        mRePassword = rePassword;
    }

    public LatLng getLatLng(){
        return new LatLng(mLatitude,mLongitude);
    }

    @Override
    public String toString() {
        return "User{" +
                "mIdUser='" + mIdUser + '\'' +
                ", mEmai='" + mEmai + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mFullName='" + mFullName + '\'' +
                ", mAge=" + mAge +
                ", mNumberPhone='" + mNumberPhone + '\'' +
                ", mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                ", mAccessToken='" + mAccessToken + '\'' +
                ", mAvatarPath='" + mAvatarPath + '\'' +
                ", mIdentificationCardBeforePath='" + mIdentificationCardBeforePath + '\'' +
                ", mIdentificationCardAfterPath='" + mIdentificationCardAfterPath + '\'' +
                ", mDrivingLicenseBeforePath='" + mDrivingLicenseBeforePath + '\'' +
                ", mDrivingLicenseAfterPath='" + mDrivingLicenseAfterPath + '\'' +
                ", mCarNumberPlatePath='" + mCarNumberPlatePath + '\'' +
                ", mIsDriving=" + mIsDriving +
                ", mNumberFavorite=" + mNumberFavorite +
                ", mNumberNotFavorite=" + mNumberNotFavorite +
                ", mIsApproved=" + mIsApproved +
                ", mIsBecome=" + mIsBecome +
                ", mRePassword='" + mRePassword + '\'' +
                '}';
    }
}
