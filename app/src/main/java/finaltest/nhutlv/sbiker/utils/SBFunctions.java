package finaltest.nhutlv.sbiker.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhutDu on 12/04/2017.
 */

public class SBFunctions {

    private Context mContext;
    private static DecimalFormat formatter = new DecimalFormat("###,###,000");

    public SBFunctions(Context context){
        mContext = context;
    }

    public static int formatDecimal(int d){
        DecimalFormat decimalFormat = new DecimalFormat(SBConstants.DECIMAL_FORMAT_DISTANCE);
        return Integer.parseInt(decimalFormat.format(d));
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static  int calculationMoney(double s){
        int total = 0;
        if(s>0 && s<=2){
            total = (int) (s*(UserLogin.getPriceList().getFirstPrice()));
        }else{
            total = (int) (2*UserLogin.getPriceList().getFirstPrice()+(s-2)*UserLogin.getPriceList().getSecondPrice());
        }
        return total;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String parseTime(int minutes){
        int hours = minutes/60;
        int minute = minutes - hours*60;
        if(hours<=0){
            return minute+"phút";
        }
        return hours +"giờ "+minute+"phút";
    }


    public void openImageIntent(Uri outputFileUri,int YOUR_SELECT_PICTURE_REQUEST_CODE) {

        // Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname ="img_"+ System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = mContext.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        ((AppCompatActivity) mContext).startActivityForResult(chooserIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
    }

    public static float getDistance2Point(LatLng newPosition,LatLng oldPosition){
        float[] results = new float[1];
        Location.distanceBetween(oldPosition.latitude, oldPosition.longitude,
                newPosition.latitude, newPosition.longitude, results);
        return results[0];
    }
}
