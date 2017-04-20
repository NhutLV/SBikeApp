package finaltest.nhutlv.sbiker.tools;

import android.content.Context;
import android.content.SharedPreferences;

import finaltest.nhutlv.sbiker.utils.UtilsConstants;

/**
 * Created by NhutDu on 18/04/2017.
 */

public class PrefManagement {

    private Context mContext;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    public PrefManagement(Context context){
        this.mContext = context;
        mPreferences = context.getSharedPreferences(UtilsConstants.SHARE_PREF_NAME,Context.MODE_PRIVATE);
    }

    public void putValueString(String key,String value){
        mEditor = mPreferences.edit();
        mEditor.putString(key,value);
        mEditor.apply();
    }

    public String getValueString(String key){
        return mPreferences.getString(key,UtilsConstants.PREF_VALUE_STRING_DEFAULT);
    }

    public void putValueInteger(String key,int value){
        mEditor = mPreferences.edit();
        mEditor.putInt(key,value).apply();
    }

    public int getValueInteger(String key){
        return mPreferences.getInt(key,UtilsConstants.PREF_VALUE_INTEGER_DEFAULT);
    }
}
