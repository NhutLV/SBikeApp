package finaltest.nhutlv.sbiker.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.UserServiceImpl;
import finaltest.nhutlv.sbiker.dialog.ChangePasswordDialog;
import finaltest.nhutlv.sbiker.dialog.ErrorDialog;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.SBConstants;
import finaltest.nhutlv.sbiker.utils.UserLogin;

/**
 * Created by NhutDu on 31/03/2017.
 */
public class ProfileActivity extends AppCompatActivity {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    @BindView(R.id.ed_name_profile)
    EditText mEdName;

    @BindView(R.id.ed_email_profile)
    EditText mEdEmail;

    @BindView(R.id.ed_number_phone_profile)
    EditText mEdNumberPhone;

    @BindView(R.id.img_avatar_profile)
    CircleImageView mImgAvatar;

    @BindView(R.id.btn_update_information)
    TextView mBtnUpdate;

    @BindView(R.id.btn_change_pass)
    Button mBtnChangePass;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Uri outputFileUri;
    private UserServiceImpl mUserService;
    private static final String TAG = ProfileActivity.class.getSimpleName();
    private String name;
    private String numberphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Ion.getDefault(this).configure().setLogging("ion-sample", Log.DEBUG);

        mToolbar.setTitle("Profile Information");
        mEdName.addTextChangedListener(new MyTextWatcher(mEdName));
        mEdEmail.addTextChangedListener(new MyTextWatcher(mEdEmail));
        mEdNumberPhone.addTextChangedListener(new MyTextWatcher(mEdNumberPhone));

        mEdEmail.setText(UserLogin.getUserLogin().getEmai());
        mEdName.setText(UserLogin.getUserLogin().getFullName());
        mEdNumberPhone.setText(UserLogin.getUserLogin().getNumberPhone()==null?
                "":UserLogin.getUserLogin().getNumberPhone());

        mImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pickImageAvatar();
            }
        });
        mUserService = new UserServiceImpl();
        mToolbar.setTitle(getResources().getString(R.string.title_profile));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(UserLogin.getUserLogin().getTypeUser()==1){
            mBtnChangePass.setEnabled(false);
        }
        mBtnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangePasswordDialog(getContext()).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;

    }

    private void openImageIntent() {
        // Determine Uri of camera image to save.
        final File root = new File(Environment.DIRECTORY_PICTURES + File.separator + "MyDir" + File.separator);
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

        startActivityForResult(chooserIntent, SBConstants.IMAGE_AVATAR_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == SBConstants.IMAGE_AVATAR_REQUEST_CODE) {
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
                mImgAvatar.setImageURI(selectedImageUri);
                mUserService.updateImage(selectedImageUri, new Callback<User>() {
                    @Override
                    public void onResult(User user) {
                        Log.d(TAG, "onResult: ");
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.d(TAG, "onFailure: "+message);
                    }
                });
            }else if(requestCode==200) {
                final Uri selectedImage = data.getData();
                new UserServiceImpl(getContext()).updateImage(selectedImage, new Callback<User>() {
                    @Override
                    public void onResult(User user) {
                        Log.d(TAG, "onResult: ");
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.d(TAG, "onFailure: "+message);
                    }
                });
            }
    }

    public void pickImageAvatar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //endregion

   private class  MyTextWatcher implements TextWatcher{

        View mView;

        public MyTextWatcher(View view) {
            mView = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            name = UserLogin.getUserLogin().getFullName();
            numberphone = UserLogin.getUserLogin().getNumberPhone();

            switch (mView.getId()){
                case R.id.ed_name_profile :
                    if(!((EditText)mView).getText().toString().equals(name)){
                        mBtnUpdate.setEnabled(true);
                    }else{
                        mBtnUpdate.setEnabled(false);
                    }
                    break;
                case R.id.ed_number_phone_profile:
                    if(!((EditText)mView).getText().toString().equals(numberphone)){
                        mBtnUpdate.setEnabled(true);
                    }else{
                        mBtnUpdate.setEnabled(false);
                    }
                    break;
            }
            mBtnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUserService.updateProfile(UserLogin.getUserLogin().getIdUser(), mEdName.getText().toString(),
                            mEdNumberPhone.getText().toString(), new Callback<User>() {
                                @Override
                                public void onResult(User user) {
                                    name = mEdName.getText().toString();
                                    numberphone = mEdNumberPhone.getText().toString();
                                    mBtnUpdate.setEnabled(false);
                                    Toast.makeText(getContext(),"Cập nhật thông tin thành công",Toast.LENGTH_LONG).show();
                                    UserLogin.setUserLogin(user);
                                }

                                @Override
                                public void onFailure(String message) {
                                    new ErrorDialog(getContext(),message).show();
                                }
                            });
                }
            });
        }
    }

    //get context activity
    private Context getContext(){
        return  this;
    }
}



