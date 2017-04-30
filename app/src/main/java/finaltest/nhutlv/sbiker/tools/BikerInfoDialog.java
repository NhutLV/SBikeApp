package finaltest.nhutlv.sbiker.tools;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.User;
import finaltest.nhutlv.sbiker.services.cloud.UserServiceImpl;

/**
 * Created by NhutDu on 14/04/2017.
 */

public class BikerInfoDialog extends Dialog {

    private User mUser;
    private myClickListener myClickListener;
    private Context mContext;
    private CheckBox mCheckFavorite;
    private UserServiceImpl mUserService;

    public interface myClickListener{
        public void onButtonClick(User user);
        public void onCheckBox(User user);
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
        mCheckFavorite = (CheckBox) findViewById(R.id.check_favorite);
        mCheckFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    myClickListener.onCheckBox(mUser);
                }else{

                }
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
