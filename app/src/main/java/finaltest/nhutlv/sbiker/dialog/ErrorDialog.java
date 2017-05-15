package finaltest.nhutlv.sbiker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import finaltest.nhutlv.sbiker.R;

/**
 * Created by NhutDu on 21/04/2017.
 */

public class ErrorDialog extends Dialog {

    private Button mBtnDismiss;
    private TextView mTxtMessage;
    private String message;
    private String txtButton = null;

    public ErrorDialog(@NonNull Context context) {
        super(context);
    }
    public ErrorDialog(@NonNull Context context,String message) {
        super(context);
        this.message = message;
    }

    public ErrorDialog(@NonNull Context context,String message, String txtButton) {
        super(context);
        this.message = message;
        this.txtButton = txtButton;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_error);
        mBtnDismiss = (Button) findViewById(R.id.btnDismissError);
        if(txtButton !=null){
            mBtnDismiss.setText(txtButton);
        }
        mTxtMessage = (TextView) findViewById(R.id.message_error);
        mTxtMessage.setText(message);
        mBtnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
