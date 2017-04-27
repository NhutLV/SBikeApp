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

/**
 * Created by NhutDu on 26/04/2017.
 */

public class ChangePasswordDialog extends Dialog{

    private Context mContext;
    private EditText mPassOld;
    private EditText mPassNew;
    private EditText mRePass;
    private Button mBtnChangePass;

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

        mPassOld.setTransformationMethod(new PasswordTransformationMethod());
        mPassNew.setTransformationMethod(new PasswordTransformationMethod());
        mRePass.setTransformationMethod(new PasswordTransformationMethod());

        mPassNew.addTextChangedListener(new MyTextWatcher(mPassNew));
        mPassOld.addTextChangedListener(new MyTextWatcher(mPassOld));
        mRePass.addTextChangedListener(new MyTextWatcher(mRePass));

        mBtnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    private boolean validatePassNew() {
        if (TextUtils.isEmpty(mPassNew.getText().toString())) {
            mPassNew.setError("Mật khẩu mới chưa hợp lệ");
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
        if (TextUtils.isEmpty(mPassNew.getText().toString())) {
            mRePass.setError("Bạn chưa nhập mật khẩu cũ");
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

    private void submitForm() {
        if (!validatePassOld()) {
            return;
        }
        if (!validatePassNew()) {
            return;
        }
        if (!validateRepass()) {
            return;
        }

        Toast.makeText(mContext.getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
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
