package finaltest.nhutlv.sbiker.tools;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
        public void onButtonClick(User user);
        public void onCheckBox(User user, boolean isCheck);
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
        mCheckFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int countF = mCount;
                boolean checkF = check;
//                if(checkF==isChecked){
//                    Log.d(TAG, "onCheckedChanged: Favorite");
//                    countF = countF+1;
//                    count.setText(String.valueOf(countF));
//                    checkF = !checkF;
//                }else{
//                    Log.d(TAG, "onCheckedChanged: No Favorite");
//                    countF = countF -1;
//                    count.setText(String.valueOf(countF));
//                    checkF = !checkF;
//                }
                myClickListener.onCheckBox(mUser,isChecked);
            }
        });
        numberPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onButtonClick(mUser);
            }
        });
    }

    private void getFavorite(){

    }
}
