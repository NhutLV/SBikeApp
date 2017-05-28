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

    @SerializedName("type_user")
    private int mTypeUser;

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

    @SerializedName("number_card")
    private String mNumberCard;

    @SerializedName("identification_number")
    private String mNumberIdentification;

    @SerializedName("identification_place")
    private String mPlaceIdentification;

    @SerializedName("identification_date")
    private String mDateIdentification;

    @SerializedName("driving_license_number")
    private String mNumberLicense;

    @SerializedName("driving_license_seri")
    private String mSeriLicense;

    @SerializedName("is_driving")
    private int mIsDriving;

    @SerializedName("is_approved")
    private int mIsApproved;

    @SerializedName("is_become")
    private int mIsBecome;

    private String mRePassword;

    public int getTypeUser() {
        return mTypeUser;
    }

    public void setTypeUser(int typeUser) {
        mTypeUser = typeUser;
    }

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

    public String getNumberCard() {
        return mNumberCard;
    }

    public void setNumberCard(String numberCard) {
        mNumberCard = numberCard;
    }

    public String getNumberIdentification() {
        return mNumberIdentification;
    }

    public void setNumberIdentification(String numberIdentification) {
        mNumberIdentification = numberIdentification;
    }

    public String getPlaceIdentification() {
        return mPlaceIdentification;
    }

    public void setPlaceIdentification(String placeIdentification) {
        mPlaceIdentification = placeIdentification;
    }

    public String getDateIdentification() {
        return mDateIdentification;
    }

    public void setDateIdentification(String dateIdentification) {
        mDateIdentification = dateIdentification;
    }

    public String getNumberLicense() {
        return mNumberLicense;
    }

    public void setNumberLicense(String numberLicense) {
        mNumberLicense = numberLicense;
    }

    public String getSeriLicense() {
        return mSeriLicense;
    }

    public void setSeriLicense(String seriLicense) {
        mSeriLicense = seriLicense;
    }

    public int getIsDriving() {
        return mIsDriving;
    }

    public void setIsDriving(int isDriving) {
        mIsDriving = isDriving;
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

    public void setLatLng(LatLng latLng){
        mLatitude = latLng.latitude;
        mLongitude = latLng.longitude;
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
                ", mIsDriving=" + mIsDriving +
                ", mIsApproved=" + mIsApproved +
                ", mIsBecome=" + mIsBecome +
                ", mRePassword='" + mRePassword + '\'' +
                '}';
    }
}
