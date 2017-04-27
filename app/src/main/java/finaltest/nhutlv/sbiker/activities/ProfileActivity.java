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
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.tools.ChangePasswordDialog;
import finaltest.nhutlv.sbiker.utils.SBConstants;
import finaltest.nhutlv.sbiker.utils.SBFunctions;

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
    Button mBtnUpdate;

    private Uri outputFileUri;
    private static final String TAG = ProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setTitle("Profile Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEdName.addTextChangedListener(new MyTextWatcher(mEdName));
        mEdEmail.addTextChangedListener(new MyTextWatcher(mEdEmail));
        mEdNumberPhone.addTextChangedListener(new MyTextWatcher(mEdNumberPhone));
        mImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openImageIntent();
            }
        });
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: aaaa");
        switch (item.getItemId()){
            case R.id.action_settings:
                Log.d(TAG, "onOptionsItemSelected: ");
                new ChangePasswordDialog(ProfileActivity.this).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;

    }

    private void openImageIntent() {
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

        startActivityForResult(chooserIntent, SBConstants.IMAGE_AVATAR_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
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
            }
        }
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
            switch (mView.getId()){
                case R.id.ed_name_profile :
                    if(!((EditText)mView).getText().toString().equals("Lê Viết Nhựt")){
                        mBtnUpdate.setEnabled(true);
                    }else{
                        mBtnUpdate.setEnabled(false);
                    }
                    break;
                case R.id.ed_email_profile :
                    if(!((EditText)mView).getText().toString().equals("levietnhutit@gmail.com")){
                        mBtnUpdate.setEnabled(true);
                    }else{
                        mBtnUpdate.setEnabled(false);
                    }
                    break;
                case R.id.ed_number_phone_profile:
                    if(!((EditText)mView).getText().toString().equals("01687184516")){
                        mBtnUpdate.setEnabled(true);
                    }else{
                        mBtnUpdate.setEnabled(false);
                    }
                    break;
            }
            mBtnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG","Active");
                }
            });
        }
    }
}



