package finaltest.nhutlv.sbiker.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.UserServiceImpl;
import finaltest.nhutlv.sbiker.tools.ErrorDialog;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.UserLogin;

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
    private ImageView mImgIndetBef;
    private ImageView mImgIndetAft;
    private EditText mEdIdentNumber;
    private EditText mEdIdentDate;
    private EditText mEdIdentPlace;
    private EditText mEdLicenseNumber;
    private EditText mEdLicenseSeri;
    private EditText mEdNumberCard;
    private Button mSubmit;
    private int yearCurrent;
    private int monthCurrent;
    private int dateCurrent;
    private UserServiceImpl mUserService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_driver);
        bindActivity();
        mUserService = new UserServiceImpl();

        Calendar calendar = Calendar.getInstance();
        yearCurrent =calendar.get(Calendar.YEAR);
        monthCurrent = calendar.get(Calendar.MONTH);
        dateCurrent = calendar.get(Calendar.DAY_OF_MONTH);
        mImgIndetAft.setOnClickListener(this);
        mImgIndetBef.setOnClickListener(this);
        mEdIdentDate.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
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
            case R.id.bc_identification_date:
                DatePickerDialog timePickerDialogClose = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        yearCurrent = year;
                        monthCurrent = month;
                        dateCurrent = dayOfMonth;
                        mEdIdentDate.setText(dayOfMonth+"/"+month+"/"+year);
                    }

                }, yearCurrent, monthCurrent, dateCurrent);
                timePickerDialogClose.show();
                break;
            case R.id.btn_become_driver:
                submitForm();
                UserLogin.getUserLogin().setIsBecome(1);
                String identificationNumber = mEdIdentNumber.getText().toString();
                String identificationPlace = mEdIdentPlace.getText().toString();
                String identificationDate = mEdIdentDate.getText().toString();
                String licenseNumber = mEdLicenseNumber.getText().toString();
                String licenseSeri = mEdLicenseSeri.getText().toString();
                String numberCard = mEdNumberCard.getText().toString();
                mUserService.sendBecomeInformation(UserLogin.getUserLogin().getIdUser(),identificationNumber,
                        identificationPlace,identificationDate,licenseNumber,licenseSeri,numberCard,1, new Callback<User>() {
                    @Override
                    public void onResult(User user) {
                        UserLogin.setUserLogin(user);
                        Toast.makeText(getContext(),"Đã gởi thông tin thành công\nVui lòng chờ ban kiểm duyệt xác nhận",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String message) {
                        new ErrorDialog(getContext(),message).show();
                    }
                });
                break;
        }
    }

    private void bindActivity(){
        mImgIndetAft = (ImageView) findViewById(R.id.image_identification_after);
        mImgIndetBef = (ImageView) findViewById(R.id.image_identification_before);
        mEdIdentNumber = (EditText) findViewById(R.id.bc_identification_card);
        mEdIdentDate = (EditText) findViewById(R.id.bc_identification_date);
        mEdIdentPlace = (EditText) findViewById(R.id.bc_identification_place);
        mEdLicenseNumber = (EditText) findViewById(R.id.bc_license_number);
        mEdLicenseSeri = (EditText) findViewById(R.id.bc_license_seri);
        mEdNumberCard = (EditText) findViewById(R.id.bc_number_card);
        mSubmit = (Button) findViewById(R.id.btn_become_driver);

    }

    private void submitForm(){

        if(!validateIdentification()){
            return ;
        }
        if(!validateLicense()){
            return ;
        }
        if(!validateNumberCard()){
            return ;
        }
        Toast.makeText(this,"Submit Form OK",Toast.LENGTH_LONG).show();
    }

    private boolean validateIdentification(){
        if(TextUtils.isEmpty(mEdIdentNumber.getText().toString())){
            new ErrorDialog(this,"Vui lòng nhập số CMND").show();
            return false;
        }
        if(TextUtils.isEmpty(mEdIdentDate.getText().toString())){
            new ErrorDialog(this,"Vui lòng nhập ngày cấp CMND").show();
            return false;
        }
        if(TextUtils.isEmpty(mEdIdentPlace.getText().toString())){
            new ErrorDialog(this,"Vui lòng nhập nơi cấp CMND").show();
            return false;
        }

        return true;
    }

    private boolean validateLicense(){
        if(TextUtils.isEmpty(mEdLicenseNumber.getText().toString())){
            new ErrorDialog(this,"Vui lòng nhập số GPLX").show();
            return false;
        }
        if(TextUtils.isEmpty(mEdLicenseSeri.getText().toString())){
            new ErrorDialog(this,"Vui lòng nhập số Seri GPLX").show();
            return false;
        }
        return true;
    }

    private boolean validateNumberCard(){
        if(TextUtils.isEmpty(mEdNumberCard.getText().toString())){
            new ErrorDialog(this,"Vui lòng nhập biển số xe").show();
            return false;
        }
        return true;
    }

    //get Context
    private Context getContext(){
        return this;
    }
}
