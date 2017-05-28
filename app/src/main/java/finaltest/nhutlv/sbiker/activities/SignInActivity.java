package finaltest.nhutlv.sbiker.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
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
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.SignInServiceImpl;
import finaltest.nhutlv.sbiker.services.cloud.SignUpServiceImpl;
import finaltest.nhutlv.sbiker.dialog.ErrorDialog;
import finaltest.nhutlv.sbiker.dialog.FlowerDialog;
import finaltest.nhutlv.sbiker.tools.PrefManagement;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.SBConstants;
import finaltest.nhutlv.sbiker.utils.UserLogin;
import finaltest.nhutlv.sbiker.utils.SBFunctions;

/**
 * Created by NhutDu on 01/03/2017.
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = SignInActivity.class.getSimpleName();
    private boolean isReceiverRegistered;

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
    Button mBtnLoginGmail;

    private CallbackManager mCallbackManager;

    private ProgressDialog mProgressDialog;
    private FlowerDialog mFlowerDialog;
    private SignInServiceImpl mLoginService;
    private SignUpServiceImpl mSignUpService;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;
    private PrefManagement mPrefManagement;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mFlowerDialog = new FlowerDialog(getContext(), "Login");
        mPassword.setTransformationMethod(new PasswordTransformationMethod());
        mPrefManagement = new PrefManagement(this);
        Intent intent = getIntent();
        if (intent.getStringExtra("email") != null) {
            mEmail.setText(intent.getStringExtra("email"));
        } else {
            mEmail.setText(mPrefManagement.getValueString(SBConstants.PREF_EMAIL));
            mPassword.setText(mPrefManagement.getValueString(SBConstants.PREF_PASSWORD));
        }
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
        mBtnLoginGmail.setOnClickListener(this);

        mNavigateSignUp.setOnClickListener(this);
        mLoginService = new SignInServiceImpl(this);
        mSignUpService = new SignUpServiceImpl();

    }


    private void navigateToHome() {
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    private void navigateToSignUp() {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (isOffline()) {
                    return;
                }
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                if (submitLogin()) {
                    mFlowerDialog.showDialog();
                    mLoginService.login(email, password, new Callback<User>() {
                        @Override
                        public void onResult(User user) {
                            mFlowerDialog.hideDialog();
                            UserLogin.setUserLogin(user);
                            mPrefManagement.putValueString(SBConstants.PREF_EMAIL, mEmail.getText().toString());
                            mPrefManagement.putValueString(SBConstants.PREF_PASSWORD, mPassword.getText().toString());
                            Log.d("TAGGGGGGGGG", user.toString());
                            navigateToHome();
                        }

                        @Override
                        public void onFailure(String message) {
                            mFlowerDialog.hideDialog();
                            new ErrorDialog(SignInActivity.this, message).show();
                        }
                    });
                }
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
        Log.d(TAG, "validateEmail: " + email);
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            new ErrorDialog(this, "Email không hợp lệ !!").show();
            return true;
        }
        return false;
    }

    private boolean validatePassword() {
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            new ErrorDialog(this, "Password không hợp lệ !!").show();
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
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
        if (isOffline()) {
            return;
        }
        if (mPrefManagement.getValueString(SBConstants.PREF_AUTO_LOGIN).length() > 0)
            signInWithAccessToken();
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
//            // and the GoogleSignInResult will be available instantly.
//            Log.d(TAG, "Got cached sign-in");
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//        } else {
//            // If the user has not previously signed in on this device or the sign-in has expired,
//            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
//            // single sign-on will occur in this branch.
//            showProgressDialog();
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
//                    handleSignInResult(googleSignInResult);
//                }
//            });
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void signInFaceBook() {
        if (isOffline()) {
            return;
        }
        mBtnLoginFb.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("SignInActivity", response.toString());

                                // Application code
                                String email = object.optString("email");
                                String birthday = object.optString("birthday"); // 01/31/1980 format
                                final String name = object.optString("name");
                                String first_name = object.optString("first_name");
                                String last_name = object.optString("last_name");
                                final String id = object.optString("id");
                                String profilePicUrl = "";
                                JSONObject data = response.getJSONObject();
                                if (data.has("picture")) {
                                    try {
                                        profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                        Log.d("TAGGGGGG", profilePicUrl);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                final String accessToken = loginResult.getAccessToken().getToken();
                                User user = new User();
                                user.setFullName(name);
                                user.setAccessToken(id);
                                user.setEmai(email);
                                user.setAvatarPath("https://graph.facebook.com/" + id + "/picture?type=large");
                                mSignUpService.signUpSocial(user, new Callback<User>() {
                                    @Override
                                    public void onResult(User user) {
                                        mPrefManagement.putValueString(SBConstants.PREF_ACCESS_TOKEN, id);
                                        mPrefManagement.putValueString(SBConstants.PREF_AUTO_LOGIN, name);
                                        UserLogin.setUserLogin(user);
                                        navigateToHome();
                                        Toast.makeText(SignInActivity.this, "Login with Facebook is successfully !", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(String message) {
                                        Log.d(TAG, "onFailure: Login Facebook");
                                        new ErrorDialog(SignInActivity.this, "Không thể kết nối máy chủ").show();
                                    }
                                });
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

    private void signInWithAccessToken() {
        String accessToken = mPrefManagement.getValueString(SBConstants.PREF_ACCESS_TOKEN);
        mFlowerDialog.showDialog();
        Log.d(TAG, "signInWithAccessToken: " + accessToken);
        mLoginService.signInSocial(accessToken, new Callback<User>() {
            @Override
            public void onResult(User user) {
//                Log.d(TAG,user.getAvatarPath());
                mFlowerDialog.hideDialog();
                UserLogin.setUserLogin(user);
                navigateToHome();
            }

            @Override
            public void onFailure(String message) {
                mFlowerDialog.hideDialog();
                new ErrorDialog(SignInActivity.this, message).show();
            }

        });
    }

    private void signInGmail() {
        if (isOffline()) {
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
                final String personName = acct.getDisplayName();
                String personPhotoUrl = acct.getPhotoUrl().toString();
                String email = acct.getEmail();
                final String tokenServer = acct.getServerAuthCode();

                User user = new User();
                user.setAvatarPath(personPhotoUrl);
                user.setFullName(personName);
                user.setEmai(email);
                user.setAccessToken(tokenServer);

                Log.e(TAG, "Author Server: " + tokenServer);
                mSignUpService.signUpSocial(user, new Callback<User>() {
                    @Override
                    public void onResult(User user) {
                        mPrefManagement.putValueString(SBConstants.PREF_ACCESS_TOKEN, tokenServer);
                        mPrefManagement.putValueString(SBConstants.PREF_AUTO_LOGIN, personName);
                        UserLogin.setUserLogin(user);
                        navigateToHome();
                        Toast.makeText(SignInActivity.this, "Login with Facebook is successfully !", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.d(TAG, "onFailure: Login Facebook");
                        new ErrorDialog(SignInActivity.this, "Không thể kết nối máy chủ").show();
                    }
                });
                updateUI(true);
            } else {
                // Signed out, show unauthenticated UI.
                updateUI(false);
            }
        }
    }

    private void updateUI(boolean boo) {
        if (boo) {
            Log.d(TAG, "Update UI TRUE");
        } else {
            Log.d(TAG, "Update UI FALSE");
        }
    }

    private void signInWithToken() {
        if (AccessToken.getCurrentAccessToken() != null) {
            Profile profile = Profile.getCurrentProfile();
            Log.d("TAG LOGIN FB", "Login Again Successfully");
        }
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

    private boolean isOffline() {
        boolean isOnline = new SBFunctions(SignInActivity.this).isOnline();
        Log.d(TAG, "isOffline: " + isOnline);
        if (!isOnline) {
            Toast.makeText(SignInActivity.this, "No connect internet ", Toast.LENGTH_LONG).show();
        }
        return !isOnline;
    }

    private Context getContext() {
        return this;
    }
}

