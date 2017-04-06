package finaltest.nhutlv.sbiker;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import finaltest.nhutlv.sbiker.services.cloud.LoginServiceImpl;
import finaltest.nhutlv.sbiker.utils.CustomDialog;
import finaltest.nhutlv.sbiker.utils.CustomToast;

/**
 * Created by NhutDu on 01/03/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.input_email_login)
    EditText mEmail;

    @BindView(R.id.input_password_login)
    EditText mPassword;

    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @BindView(R.id.link_signup)
    TextView mNavigateSignUp;

    @BindView(R.id.progressBarLogin)
    ProgressBar mProgressBar;

    @BindView(R.id.layout_login)
    RelativeLayout mLayout;

    AlertDialog mAlertDialog;

    CustomDialog mCustomDialog;
    LoginServiceImpl mLoginService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mBtnLogin.setOnClickListener(this);
        mNavigateSignUp.setOnClickListener(this);
        mLoginService = new LoginServiceImpl();
        mAlertDialog  = new SpotsDialog(this,"Login...");
        mCustomDialog = new CustomDialog(this,"Login...");

    }

    public void showProgress() {
        mCustomDialog.showDialog();
    }


    public void hideProgress() {
        mCustomDialog.dismissDialog();
    }


    public void setEmailError() {
        new CustomToast().ShowToast(this,mLayout,"Email chưa hợp lệ");
    }


    public void setPasswordError() {
        new CustomToast().ShowToast(this,mLayout,"Password chưa hợp lệ");
    }


    public void navigateToHome() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }


    public void navigateToSignUp() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                /*
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                if(submitLogin()){
                    mCustomDialog.showDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mLoginService.activity_login(email, password, new Callback<User>() {
                                @Override
                                public void onResult(User user) {
                                    if (user != null) {
                                        new CustomToast().ShowToast(getApplicationContext(),mLayout,"Login is successfully !!!");
                                    } else {
                                        new CustomToast().ShowToast(getApplicationContext(),mLayout,"Email or Password is not correct");
                                    }
                                }
                                @Override
                                public void onFailure() {
                                    new CustomToast().ShowToast(getApplicationContext(),mLayout,"Login is Failed");
                                }
                            });
                            mCustomDialog.dismissDialog();
                        }
                    }, 3000);
                }

//                mLoginPresenter.validateLogin(mEmail.getText().toString(),mPassword.getText().toString());
*/
                navigateToHome();
                break;

            case R.id.link_signup:
                navigateToSignUp();
                break;
        }
    }

    private boolean validateEmail(){
        String email = mEmail.getText().toString();
        if(TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           new CustomToast().ShowToast(this,mLayout,"Email không hợp lệ");
           return true;
        }
        return false;
    }

    private boolean validatePassword(){
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            new CustomToast().ShowToast(this,mLayout,"Password không hợp lệ");
            return true;
        }
        return false;
    }


    private boolean submitLogin(){
        if(validateEmail()){
            return false;
        }

        if(validatePassword()){
            return false;
        }
        return true;
    }

}
