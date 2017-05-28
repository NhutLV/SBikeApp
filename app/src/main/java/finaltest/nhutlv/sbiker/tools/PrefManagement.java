package finaltest.nhutlv.sbiker.tools;

import android.content.Context;
import android.content.SharedPreferences;

import finaltest.nhutlv.sbiker.utils.SBConstants;

/**
 * Created by NhutDu on 18/04/2017.
 */

public class PrefManagement {

    private Context mContext;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    public PrefManagement(Context context){
        this.mContext = context;
        mPreferences = context.getSharedPreferences(SBConstants.SHARE_PREF_NAME,Context.MODE_PRIVATE);
    }

    public void putValueString(String key,String value){
        mEditor = mPreferences.edit();
        mEditor.putString(key,value);
        mEditor.apply();
    }

    public String getValueString(String key){
        return mPreferences.getString(key, SBConstants.PREF_VALUE_STRING_DEFAULT);
    }

    public void putValueBoolean(String key,boolean value){
        mEditor = mPreferences.edit();
        mEditor.putBoolean(key,value);
        mEditor.apply();
    }

    public boolean getValueBoolean(String key){
        return mPreferences.getBoolean(key, SBConstants.PREF_VALUE_BOOLEAN_DEFAULT);
    }

    public void putValueInteger(String key,int value){
        mEditor = mPreferences.edit();
        mEditor.putInt(key,value).apply();
    }

    public int getValueInteger(String key){
        return mPreferences.getInt(key, SBConstants.PREF_VALUE_INTEGER_DEFAULT);
    }
}
