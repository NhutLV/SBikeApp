package finaltest.nhutlv.sbiker.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by NhutDu on 12/03/2017.
 */

public class CustomDialog {

    ProgressDialog progressDialog;

    public CustomDialog(Context context,String message){
        progressDialog = new ProgressDialog(context);
//        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
    }

    public void showDialog(){
        progressDialog.show();
    }

    public void dismissDialog(){
        progressDialog.dismiss();
    }

}
