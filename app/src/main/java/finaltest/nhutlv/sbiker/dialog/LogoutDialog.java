package finaltest.nhutlv.sbiker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import finaltest.nhutlv.sbiker.R;

/**
 * Created by NhutDu on 13/05/2017.
 */

public class LogoutDialog extends Dialog {

    public interface onMyListenerLogout{
        void onLogout();
    }

    private onMyListenerLogout mOnMyListenerLogout;

    public LogoutDialog(@NonNull Context context, onMyListenerLogout onMyListenerLogout) {
        super(context);
        mOnMyListenerLogout = onMyListenerLogout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_logout);
        Button btnLogout = (Button) findViewById(R.id.btn_dialog_logout);
        Button btnStay = (Button) findViewById(R.id.btn_dialog_stay);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMyListenerLogout.onLogout();
            }
        });

        btnStay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
