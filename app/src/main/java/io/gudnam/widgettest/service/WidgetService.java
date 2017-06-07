package io.gudnam.widgettest.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import io.gudnam.widgettest.MyWidget1x2;
import io.gudnam.widgettest.preference.PreferenceHelper;
import io.gudnam.widgettest.preference.WidgetStatusPreference;

import static io.gudnam.widgettest.MyWidget1x2.PARM_WIDGET_ID;

public class WidgetService extends Service {

    private static String TAG = WidgetService.class.getSimpleName();

    int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        PreferenceHelper.setContext(this);
        final WidgetStatusPreference preference = new WidgetStatusPreference();

        appWidgetId = (int) intent.getSerializableExtra(PARM_WIDGET_ID);
        Log.i(TAG, "start command (id" + appWidgetId + ")");
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
            return START_NOT_STICKY;

        String status = preference.getAction(appWidgetId);
        switch (status) {
            case MyWidget1x2.STATUS_DISABLED:
                preference.setAction(appWidgetId, MyWidget1x2.STATUS_CONNECTING);
                break;
        }

        sendMessage();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preference.setAction(appWidgetId, MyWidget1x2.STATUS_CONNECTED);
                sendMessage();
            }
        }, 3000);


        return START_NOT_STICKY;
    }

    private void sendMessage() {
        Intent next = new Intent();
        next.setAction(MyWidget1x2.ACTION_WIDGET_STATE);
        sendBroadcast(next);
    }

}
