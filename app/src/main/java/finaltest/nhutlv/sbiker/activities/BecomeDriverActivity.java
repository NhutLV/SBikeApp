package finaltest.nhutlv.sbiker.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import finaltest.nhutlv.sbiker.R;

/**
 * Created by NhutDu on 25/04/2017.
 */

public class BecomeDriverActivity extends AppCompatActivity implements View.OnClickListener{
    private Uri outputFileUri;
    private static final int IDENTIFICATION_CARD_BEFORE_REQUEST_CODE = 100;
    private static final int IDENTIFICATION_CARD_AFTER_REQUEST_CODE = 101;
    private static final int DRIVING_LICENSE_BEFORE_REQUEST_CODE = 102;
    private static final int DRIVING_LICENSE_AFTER_REQUEST_CODE = 103;
    private static final int CARD_NUMBER_REQUEST_CODE = 104;
    private static final String TAG = BecomeDriverActivity.class.getSimpleName();
    ImageView mImgIndetBef;
    ImageView mImgIndetAft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_driver);
        mImgIndetAft = (ImageView) findViewById(R.id.image_identification_after);
        mImgIndetBef = (ImageView) findViewById(R.id.image_identification_before);
        mImgIndetAft.setOnClickListener(this);
        mImgIndetBef.setOnClickListener(this);
    }

    private void openImageIntent(int requestCode) {

        // Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname ="img_"+ System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
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

        startActivityForResult(chooserIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case IDENTIFICATION_CARD_BEFORE_REQUEST_CODE:
                    Log.d(TAG, "onActivityResult: "+IDENTIFICATION_CARD_BEFORE_REQUEST_CODE);
                    resultImage(data,mImgIndetBef);
                    break;
                case IDENTIFICATION_CARD_AFTER_REQUEST_CODE:
                    Log.d(TAG, "onActivityResult: "+IDENTIFICATION_CARD_AFTER_REQUEST_CODE);
                    resultImage(data,mImgIndetAft);
                    break;
                case DRIVING_LICENSE_BEFORE_REQUEST_CODE:
                    break;
                case DRIVING_LICENSE_AFTER_REQUEST_CODE:
                    break;
                case CARD_NUMBER_REQUEST_CODE:

                    break;
            }

        }
    }

    private void resultImage(Intent data, ImageView img){
        final boolean isCamera;
        Log.d(TAG, "onActivityResult: ");
        if (data == null) {
            Log.d(TAG, "onActivityResult: data NULL");
            isCamera = true;
        } else {
            final String action = data.getAction();
            if (action == null) {
                Log.d(TAG, "onActivityResult: action NULL");
                isCamera = false;
            } else {
                Log.d(TAG, "onActivityResult: action "+action);
                isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            }
        }

        Uri selectedImageUri;
        if (isCamera) {
            selectedImageUri = outputFileUri;
            Log.d(TAG, "onActivityResult: IS CAMERA "+selectedImageUri.toString());
        } else {
            selectedImageUri = data == null ? null : data.getData();
            Log.d(TAG, "onActivityResult: NO CAMERA "+selectedImageUri.toString());
        }
        img.setImageURI(selectedImageUri);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_identification_before:
                openImageIntent(IDENTIFICATION_CARD_BEFORE_REQUEST_CODE);
                break;
            case R.id.image_identification_after:
                openImageIntent(IDENTIFICATION_CARD_AFTER_REQUEST_CODE);
                break;
        }
    }
}
