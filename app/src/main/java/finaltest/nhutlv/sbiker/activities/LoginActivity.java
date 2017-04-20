package finaltest.nhutlv.sbiker.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.services.cloud.LoginServiceImpl;
import finaltest.nhutlv.sbiker.utils.CustomDialog;
import finaltest.nhutlv.sbiker.utils.CustomToast;
import finaltest.nhutlv.sbiker.utils.UserLogin;
import finaltest.nhutlv.sbiker.utils.UtilsFunctions;

/**
 * Created by NhutDu on 01/03/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
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

    @BindView(R.id.btn_login_facebook)
    LoginButton mBtnLoginFb;

    @BindView(R.id.btn_login_gmail)
    SignInButton mBtnLoginGmail;

    private CallbackManager mCallbackManager;

    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    private CustomDialog mCustomDialog;
    private LoginServiceImpl mLoginService;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN =1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestServerAuthCode(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mBtnLogin.setOnClickListener(this);
        mBtnLoginFb.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        signInWithToken();

        mBtnLoginFb.setOnClickListener(this);
        mBtnLoginGmail.setSize(SignInButton.COLOR_DARK);
        mBtnLoginGmail.setOnClickListener(this);

        mNavigateSignUp.setOnClickListener(this);
        mLoginService = new LoginServiceImpl();
        mAlertDialog = new SpotsDialog(this, "Login...");
        mCustomDialog = new CustomDialog(this, "Login...");
    }

    public void showProgress() {
        mCustomDialog.showDialog();
    }


    public void hideProgress() {
        mCustomDialog.dismissDialog();
    }


    public void setEmailError() {
        new CustomToast().ShowToast(this, mLayout, "Email chưa hợp lệ");
    }


    public void setPasswordError() {
        new CustomToast().ShowToast(this, mLayout, "Password chưa hợp lệ");
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
        switch (view.getId()) {
            case R.id.btn_login:
                Log.d(TAG, "onClick: ");
                if(isOffline()){
                    return;
                }
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
            case R.id.btn_login_facebook:
                signInFaceBook();
                break;
            case R.id.btn_login_gmail:
                signInGmail();
                break;
        }
    }

    private boolean validateEmail() {
        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            new CustomToast().ShowToast(this, mLayout, "Email không hợp lệ");
            return true;
        }
        return false;
    }

    private boolean validatePassword() {
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            new CustomToast().ShowToast(this, mLayout, "Password không hợp lệ");
            return true;
        }
        return false;
    }

    private boolean submitLogin() {
        if (validateEmail()) {
            return false;
        }

        if (validatePassword()) {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            UserLogin.setGoogleApiClient(mGoogleApiClient);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isOffline()){
            return;
        }
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void signInFaceBook() {
        if(isOffline()){
            return;
        }
        mBtnLoginFb.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                String email = object.optString("email");
                                String birthday = object.optString("birthday"); // 01/31/1980 format
                                String name = object.optString("name");
                                String first_name = object.optString("first_name");
                                String last_name = object.optString("last_name");
                                String id = object.optString("id");

                                Log.d("TAG LOGIN FB", email);
                                Log.d("TAG LOGIN FB", name);
                                Log.d("TAG LOGIN FB", first_name);
                                Log.d("TAG LOGIN FB", last_name);
                                Log.d("TAG LOGIN FB", id);
                                Log.d("TAG LOGIN FB", birthday);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    private void signInGmail() {
        if(isOffline()){
            return;
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                Log.e(TAG, "display name: " + acct.getDisplayName());
                String personName = acct.getDisplayName();
                String personPhotoUrl = acct.getPhotoUrl().toString();
                String email = acct.getEmail();
                String tokenServer = acct.getServerAuthCode();

                Log.e(TAG, "Name: " + personName + ", email: " + email
                        + ", Image: " + personPhotoUrl);
                Log.e(TAG, "Author Server: "+tokenServer);

                updateUI(true);
            } else {
                // Signed out, show unauthenticated UI.
                updateUI(false);
            }
        }
    }

    private void updateUI(boolean boo){
        if(boo){
            Log.d(TAG,"Update UI TRUE");
        }else{
            Log.d(TAG,"Update UI FALSE");
        }
    }

    private void signInWithToken() {
        if (AccessToken.getCurrentAccessToken() != null) {
            Profile profile = Profile.getCurrentProfile();
            Log.d("TAG LOGIN FB", "Login Again Successfully");
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading....");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: ");
    }

    private boolean isOffline(){
        boolean isOnline = new UtilsFunctions(LoginActivity.this).isOnline();
        Log.d(TAG, "isOffline: "+isOnline);
        if(!isOnline){
            Toast.makeText(LoginActivity.this,"No connect internet ",Toast.LENGTH_LONG).show();
        }
        return !isOnline;
    }
}

