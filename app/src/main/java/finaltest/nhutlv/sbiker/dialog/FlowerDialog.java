package finaltest.nhutlv.sbiker.dialog;

import android.content.Context;
import android.graphics.Color;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by NhutDu on 02/05/2017.
 */

public class FlowerDialog {

    private ACProgressFlower dialog;
    private Context mContext;
    public FlowerDialog(Context context){
        mContext = context;
        dialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
    }

    public FlowerDialog(Context context, String text){
        mContext = context;
        dialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(text)
                .fadeColor(Color.DKGRAY).build();
    }

    public void showDialog(){
        dialog.show();
    }

    public void hideDialog(){
        dialog.dismiss();
    }
}
