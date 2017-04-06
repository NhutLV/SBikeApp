package finaltest.nhutlv.sbiker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.entities.Coordinate;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.utils.CustomToast;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mBtnSubmit.setOnClickListener(this);
        mNavigateLogin.setOnClickListener(this);
    }


    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }


    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }


    public void setFullNameError() {
        new CustomToast().ShowToast(this,mLayout,"FullName nhập chưa hợp lệ ");
    }


    public void setPasswordError() {
        new CustomToast().ShowToast(this,mLayout,"Password chưa hợp lệ ");
    }


    public void setRePasswordError() {
        new CustomToast().ShowToast(this,mLayout,"Re-password không hợp lệ");
    }

    public void setConfirmPasswordError() {
        new CustomToast().ShowToast(this,mLayout,"Confirm password chưa đúng ");
    }


    public void setNumberPhoneError() {
        new CustomToast().ShowToast(this,mLayout,"Number Phone chưa hợp lệ");
    }


    public void setEmailError() {
        new CustomToast().ShowToast(this,mLayout,"Email chưa hợp lệ");
    }


    public void setAgeError() {
        new CustomToast().ShowToast(this,mLayout,"Age chưa hợp lệ");
    }



    public void navigateNext() {
        startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));
        finish();
    }


    public void navigateLogin() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }

    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_up:
                Log.d("TAG", "onClick:  Sign Up");
                User user = new User();
                user.setFullName(mFullname.getText().toString());
                user.setEmai(mEmail.getText().toString());
                user.setNumberPhone(mNumberPhone.getText().toString());
                Coordinate coordinate = new Coordinate();
                coordinate.setLatitude(16.045738);
                coordinate.setLongitude(108.228653);
                user.setCoordinate(coordinate);
                user.setPassword(mPassword.getText().toString());
                user.setRePassword(mRePassword.getText().toString());
//               mRegisterPresenter.validateRegister(user);
                break;

            case R.id.link_sign_in:
                Log.d("TAG", "onClick: Navigate Sign In");
                navigateLogin();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
