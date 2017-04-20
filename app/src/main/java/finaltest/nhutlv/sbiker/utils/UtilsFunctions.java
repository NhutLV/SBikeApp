package finaltest.nhutlv.sbiker.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DecimalFormat;

/**
 * Created by NhutDu on 12/04/2017.
 */

public class UtilsFunctions {

    private Context mContext;

    public UtilsFunctions(Context context){
        mContext = context;
    }

    public static int formatDecimal(int d){
        DecimalFormat decimalFormat = new DecimalFormat(UtilsConstants.DECIMAL_FORMAT_DISTANCE);
        return Integer.parseInt(decimalFormat.format(d));
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
