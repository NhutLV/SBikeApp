package finaltest.nhutlv.sbiker.tools;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.UserServiceImpl;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.UserLogin;

/**
 * Created by NhutDu on 26/04/2017.
 */

public class ChangePasswordDialog extends Dialog{

    private Context mContext;
    private EditText mPassOld;
    private EditText mPassNew;
    private EditText mRePass;
    private Button mBtnChangePass;
    private UserServiceImpl mUserService;

    public ChangePasswordDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_password);
        setTitle("Change Password");
        mPassOld = (EditText) findViewById(R.id.ed_password_old);
        mPassNew = (EditText) findViewById(R.id.ed_password_new);
        mRePass = (EditText) findViewById(R.id.ed_retype_password);
        mBtnChangePass = (Button) findViewById(R.id.btn_change_pass);
        mUserService = new UserServiceImpl();
        mPassOld.setTransformationMethod(new PasswordTransformationMethod());
        mPassNew.setTransformationMethod(new PasswordTransformationMethod());
        mRePass.setTransformationMethod(new PasswordTransformationMethod());

        mPassNew.addTextChangedListener(new MyTextWatcher(mPassNew));
        mPassOld.addTextChangedListener(new MyTextWatcher(mPassOld));
        mRePass.addTextChangedListener(new MyTextWatcher(mRePass));

        mBtnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(submitForm()){
                    mUserService.changePassword(UserLogin.getUserLogin().getIdUser(),mPassOld.getText().toString(),
                            UserLogin.getUserLogin().getPassword(),mPassNew.getText().toString(), new Callback<User>() {
                                @Override
                                public void onResult(User user) {
                                    dismiss();
                                    Toast.makeText(mContext,"Đổi mật khẩu thành công",Toast.LENGTH_LONG);
                                    UserLogin.setUserLogin(user);
                                }

                                @Override
                                public void onFailure(String message) {
                                    mPassOld.setError(message);
                                    requestFocus(mPassOld);
                                }
                            });
                }
            }
        });

    }

    private boolean validatePassNew() {
        if (TextUtils.isEmpty(mPassNew.getText().toString())) {
            mPassNew.setError("Vui lòng nhập mật khẩu mới");
            requestFocus(mPassNew);
            return false;
        }else{
            mPassNew.setError(null);
        }
        return true;
    }
    private boolean validatePassOld() {
        if (TextUtils.isEmpty(mPassOld.getText().toString())) {
            mPassOld.setError("Vui lòng nhập mật khẩu cũ");
            requestFocus(mPassOld);
            return false;
        }else{
            mPassOld.setError(null);
        }
        return true;
    }

    private boolean validateRepass() {
        if (TextUtils.isEmpty(mRePass.getText().toString())) {
            mRePass.setError("Vui lòng nhập xác nhận mật khẩu");
            requestFocus(mRePass);
            return false;
        }
//        }else if(!TextUtils.equals(mPassNew.getText().toString(),mRePass.getText().toString())){
//            mRePass.setError("Xác nhận mật khẩu chưa đúng");
//            requestFocus(mRePass);
//            return false;
//        }else{
        else{
            mRePass.setError(null);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean submitForm() {
        if (!validatePassOld()) {
            return false;
        }
        if (!validatePassNew()) {
            return false;
        }
        if (!validateRepass()) {
            return false;
        }
        return true;
    }

    private class  MyTextWatcher implements TextWatcher {

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
                case R.id.ed_password_new :
                    validatePassNew();
                    break;
                case R.id.ed_password_old :
                    validatePassOld();
                    break;
                case R.id.ed_retype_password:
                    validateRepass();
                    break;
            }
        }
    }

}
