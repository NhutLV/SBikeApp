package finaltest.nhutlv.sbiker.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NhutDu on 17/05/2017.
 */

public class PriceList {

    @SerializedName("_id")
    private String mId;

    @SerializedName("first_price")
    private int mFirstPrice;

    @SerializedName("second_price")
    private int mSecondPrice;

    @SerializedName("fee")
    private int mFeePrice;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public int getFirstPrice() {
        return mFirstPrice;
    }

    public void setFirstPrice(int firstPrice) {
        mFirstPrice = firstPrice;
    }

    public int getSecondPrice() {
        return mSecondPrice;
    }

    public void setSecondPrice(int secondPrice) {
        mSecondPrice = secondPrice;
    }

    public int getFeePrice() {
        return mFeePrice;
    }

    public void setFeePrice(int feePrice) {
        mFeePrice = feePrice;
    }

    public PriceList(String id, int firstPrice, int secondPrice, int feePrice) {
        mId = id;
        mFirstPrice = firstPrice;
        mSecondPrice = secondPrice;
        mFeePrice = feePrice;
    }

    public PriceList() {
    }
}
