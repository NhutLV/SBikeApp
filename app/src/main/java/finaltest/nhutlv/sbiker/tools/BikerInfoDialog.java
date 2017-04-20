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
import android.widget.TextView;

import finaltest.nhutlv.sbiker.R;
import finaltest.nhutlv.sbiker.entities.User;

/**
 * Created by NhutDu on 14/04/2017.
 */

public class BikerInfoDialog extends Dialog {

    User mUser;
    myClickListener myClickListener;
    Context mContext;

    public interface myClickListener{
        public void onButtonClick(User user);
    }

    public BikerInfoDialog(@NonNull Context context,myClickListener myClickListener,User user) {
        super(context);
        mContext = context;
        this.myClickListener = myClickListener;
        this.mUser = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_biker_info);

        TextView numberPhone = (TextView) findViewById(R.id.phone_biker_info);
        numberPhone.setText(mUser.getNumberPhone());
        TextView fullName = (TextView) findViewById(R.id.fullname_biker_info);
        fullName.setText(mUser.getFullName());
        numberPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onButtonClick(mUser);
            }
        });
    }
}
