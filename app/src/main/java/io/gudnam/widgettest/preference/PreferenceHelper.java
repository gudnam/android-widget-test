package io.gudnam.widgettest.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by continueing on 15. 9. 3..
 */
public abstract class PreferenceHelper
{
    public static Context context;

    public abstract String getFileName();

    public static void setContext(Context aContext) {
        context = aContext;
    }

    private SharedPreferences.Editor getEditor() {
        SharedPreferences pref = context.getSharedPreferences(getFileName(), Context.MODE_PRIVATE);
        return pref.edit();
    }

    public String getString(String aParm, String aDefault)
    {
        return context.getSharedPreferences(getFileName(), Context.MODE_PRIVATE).getString(aParm, aDefault);
    }

    public int getInt(String aParm, int aDefault)
    {
        return context.getSharedPreferences(getFileName(), Context.MODE_PRIVATE).getInt(aParm, aDefault);
    }

    public boolean getBoolean(String aParm, boolean aDefault)
    {
        return context.getSharedPreferences(getFileName(), Context.MODE_PRIVATE).getBoolean(aParm, aDefault);
    }

    public long getLong(String aParm, long aDefault)
    {
        return context.getSharedPreferences(getFileName(), Context.MODE_PRIVATE).getLong(aParm, aDefault);
    }

    public void setInt(String aKey, int aValue)
    {
        getEditor().putInt(aKey, aValue).commit();
    }
    public void setString(String aKey, String aValue)
    {
        getEditor().putString(aKey, aValue).commit();
    }
    public void setBoolean(String aKey, boolean aValue)
    {
        getEditor().putBoolean(aKey, aValue).commit();
    }
    public void setLong(String aKey, long aValue)
    {
        getEditor().putLong(aKey, aValue).commit();
    }
}
