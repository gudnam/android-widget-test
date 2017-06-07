package io.gudnam.widgettest.preference;

import static io.gudnam.widgettest.MyWidget1x2.STATUS_DISABLED;

/**
 * Created by continueing on 15. 9. 3..
 */
public class WidgetStatusPreference extends PreferenceHelper
{
    private static final String FILE_NAME ="WIDGET_SWITCHER_ADDRESS";
    private String KEY_WIDGET_ID = "WIDGET_ID";

    @Override
    public String getFileName()
    {
        return FILE_NAME;
    }

    public void setAction(int id, String action) {
        setString(KEY_WIDGET_ID + "." + id, action);
    }

    public String getAction(int id) {
        return getString(KEY_WIDGET_ID + "." + id, STATUS_DISABLED);
    }
}
