package finaltest.nhutlv.sbiker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.SignUpServiceImpl;
import finaltest.nhutlv.sbiker.dialog.ErrorDialog;
import finaltest.nhutlv.sbiker.dialog.FlowerDialog;
import finaltest.nhutlv.sbiker.utils.Callback;

/**
 * Created by NhutDu on 21/02/2017.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.input_name_sign_up)
    EditText mFullname;

    @BindView(R.id.input_password_sign_up)
    EditText mPassword;

    @BindView(R.id.input_number_phone_sign_up)
    EditText mNumberPhone;

    @BindView(R.id.input_confirm_password_sign_up)
    EditText mRePassword;

    @BindView(R.id.input_email_sign_up)
    EditText mEmail;

    @BindView(R.id.btn_sign_up)
    Button mBtnSubmit;

    @BindView(R.id.progressBarSignUp)
    ProgressBar mProgressBar;

    @BindView(R.id.link_sign_in)
    TextView mNavigateLogin;

    @BindView(R.id.layout_sign_up)
    LinearLayout mLayout;

    private SignUpServiceImpl mService;
    private FlowerDialog mFlowerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.sign_up));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mService = new SignUpServiceImpl();
        mFlowerDialog = new FlowerDialog(getContext(),"Sign Up");
        mBtnSubmit.setOnClickListener(this);
        mNavigateLogin.setOnClickListener(this);
        mPassword.setTransformationMethod(new PasswordTransformationMethod());
        mRePassword.setTransformationMethod(new PasswordTransformationMethod());
    }


    public Context getContext() {
        return this;
    }

    private boolean validateFullName(){
        if(TextUtils.isEmpty(mFullname.getText().toString())){
            new ErrorDialog(getContext(),"Vui lòng nhập Họ và Tên").show();
            return false;
        }
        return true;
    }

    private boolean validateEmail(){
        if(TextUtils.isEmpty(mEmail.getText().toString())){
            new ErrorDialog(getContext(),"Vui lòng nhập Email").show();
            return false;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()){
            new ErrorDialog(getContext(),"Email chưa đúng định dạng").show();
            return false;
        }

        return true;
    }

    private boolean validateNumberPhone(){
        if(TextUtils.isEmpty(mNumberPhone.getText().toString())){
            new ErrorDialog(getContext(),"Vui lòng nhập số điện thoại").show();
            return false;
        }
        return true;
    }

    private boolean validatePassword(){
        if(TextUtils.isEmpty(mPassword.getText().toString())){
            new ErrorDialog(getContext(),"Vui lòng nhập mật khẩu").show();
            return false;
        }
        return true;
    }

    private boolean validateRePassword(){
        if(TextUtils.isEmpty(mRePassword.getText().toString())){
            new ErrorDialog(getContext(),"Vui lòng nhập mật khẩu").show();
            return false;
        }else if(!mPassword.getText().toString().equals(mRePassword.getText().toString())){
            new ErrorDialog(getContext(),"Xác nhận mật khẩu không đúng").show();
            return false;
        }
        return true;
    }

    private boolean submitForm(){
        if(!validateFullName()){
            return false;
        }
        if(!validateEmail()){
            return false;
        }
        if(!validateNumberPhone()){
            return false;
        }
        if(!validatePassword()){
            return false;
        }
        if(!validateRePassword()){
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_up:
                if(submitForm()){
                    mFlowerDialog.showDialog();
                    User user = new User();
                    user.setFullName(mFullname.getText().toString());
                    user.setEmai(mEmail.getText().toString());
                    user.setNumberPhone(mNumberPhone.getText().toString());
                    user.setPassword(mPassword.getText().toString());
                    user.setRePassword(mRePassword.getText().toString());
                    mService.signUp(user, new Callback<User>() {
                        @Override
                        public void onResult(User user) {
                            mFlowerDialog.hideDialog();
                            Toast.makeText(SignUpActivity.this,"Sign up is successfully !",Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(getContext(),SignInActivity.class);
                            intent.putExtra("email",mEmail.getText().toString());
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(String message) {
                            mFlowerDialog.hideDialog();
                            new ErrorDialog(SignUpActivity.this,message).show();
                        }
                    });
                }
                break;

            case R.id.link_sign_in:
                startActivity(new Intent(getContext(),SignInActivity.class));
                finish();
                break;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
