package finaltest.nhutlv.sbiker.entities;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.android.gms.common.api.Status;

import java.io.Serializable;

/**
 * Created by NhutDu on 01/05/2017.
 */

public class SearchEntity implements Parcelable{

    private Place mPlace;
    private Status mStatus;

    public SearchEntity(Place place, Status status) {
        mPlace = place;
        mStatus = status;
    }

    protected SearchEntity(Parcel in) {
        mStatus = in.readParcelable(Status.class.getClassLoader());
    }

    public static final Creator<SearchEntity> CREATOR = new Creator<SearchEntity>() {
        @Override
        public SearchEntity createFromParcel(Parcel in) {
            return new SearchEntity(in);
        }

        @Override
        public SearchEntity[] newArray(int size) {
            return new SearchEntity[size];
        }
    };

    public Place getPlace() {
        return mPlace;
    }

    public void setPlace(Place place) {
        mPlace = place;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public SearchEntity() {
    }

    @Override
    public String toString() {
        return "SearchEntity{" +
                "mPlace=" + mPlace +
                ", mStatus=" + mStatus +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mStatus, flags);
    }
}
