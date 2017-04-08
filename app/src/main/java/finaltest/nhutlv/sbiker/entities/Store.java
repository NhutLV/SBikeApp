package finaltest.nhutlv.sbiker.entities;

/**
 * Created by NhutDu on 18/03/2017.
 */

public class Store extends BaseEntities {

    private String mQuantityStaff;

    private String mTimeOpen;

    private String mTimeClose;

    public String getQuantityStaff() {
        return mQuantityStaff;
    }

    public void setQuantityStaff(String quantityStaff) {
        mQuantityStaff = quantityStaff;
    }

    public String getTimeOpen() {
        return mTimeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        mTimeOpen = timeOpen;
    }

    public String getTimeClose() {
        return mTimeClose;
    }

    public void setTimeClose(String timeClose) {
        mTimeClose = timeClose;
    }

    public Store(String fullName, String address, String numberPhone, String type, String quantityStaff, String timeOpen, String timeClose) {
        super(fullName, address, numberPhone, type);
        mQuantityStaff = quantityStaff;
        mTimeOpen = timeOpen;
        mTimeClose = timeClose;
    }
}
