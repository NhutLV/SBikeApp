package finaltest.nhutlv.sbiker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.UserServiceImpl;
import finaltest.nhutlv.sbiker.utils.Callback;
import finaltest.nhutlv.sbiker.utils.UserLogin;

/**
 * Created by NhutDu on 14/04/2017.
 */

public class BikerInfoDialog extends Dialog {

    private User mUser;
    private myClickListener myClickListener;
    private Context mContext;
    private CheckBox mCheckFavorite;
    private UserServiceImpl mUserService;
    private static final String TAG = BikerInfoDialog.class.getSimpleName();
    private int mCount;
    private boolean check;

    public interface myClickListener{
        void onButtonClick(User user);
        void onCheckBox(User user, boolean isCheck);
    }

    public BikerInfoDialog(@NonNull Context context,myClickListener myClickListener,User user) {
        super(context);
        mContext = context;
        this.myClickListener = myClickListener;
        this.mUser = user;
        this.mUserService = new UserServiceImpl();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_demo);
        TextView numberPhone = (TextView) findViewById(R.id.phone_biker_info);
        numberPhone.setText(mUser.getNumberPhone());
        TextView fullName = (TextView) findViewById(R.id.fullname_biker_info);
        fullName.setText(mUser.getFullName());
        TextView email = (TextView) findViewById(R.id.email_biker_info);
        email.setText(mUser.getEmai());
        final TextView count = (TextView) findViewById(R.id.count_favorite_biker_info);

        mUserService.checkFavorite(UserLogin.getUserLogin().getIdUser(), mUser.getIdUser(), new Callback<Integer>() {
            @Override
            public void onResult(Integer integer) {
                check = (integer==1);
                mCheckFavorite.setChecked((integer==1));
            }
            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure: check Favorite");
            }
        });

        mUserService.getCountFavorite(mUser.getIdUser(), new Callback<Integer>() {
            @Override
            public void onResult(Integer integer) {
                count.setText(String.valueOf(integer));
                mCount = integer;
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG,"Lỗi");
            }
        });

        mCheckFavorite = (CheckBox) findViewById(R.id.check_favorite);
        mCheckFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox)v).isChecked();
                if(isChecked){
                    Log.d(TAG, "onCheckedChanged: NO CHECK");
                    count.setText(String.valueOf(mCount+1));
                    mCount = mCount + 1;
                }else{
                    Log.d(TAG, "onCheckedChanged: CHECK");
                    count.setText(String.valueOf(mCount-1));
                    mCount = mCount-1;
                }
                myClickListener.onCheckBox(mUser,isChecked);
            }
        });
//        mCheckFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int countF = mCount;
//                boolean checkF = check;
//                if(!isChecked){
//                    Log.d(TAG, "onCheckedChanged: CHECK");
//                    count.setText(String.valueOf(mCount-1));
//                    mCount = mCount-1;
//                }else{
//                    Log.d(TAG, "onCheckedChanged: NO CHECK");
//                    count.setText(String.valueOf(mCount+1));
//                    mCount = mCount + 1;
//                }
//                myClickListener.onCheckBox(mUser,isChecked);
//            }
//        });
        numberPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onButtonClick(mUser);
                dismiss();
            }
        });
    }

    private void getFavorite(){

    }
}
