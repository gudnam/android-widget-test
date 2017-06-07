package io.gudnam.widgettest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import io.gudnam.widgettest.preference.PreferenceHelper;
import io.gudnam.widgettest.preference.WidgetStatusPreference;
import io.gudnam.widgettest.service.WidgetService;

/**
 * Created by gudnam on 2017. 5. 22..
 */

public class MyWidget1x2 extends AppWidgetProvider {

    public static final String ACTION_WIDGET_STATE = "io.gudnam.widgettest.MyWidget1x2.WIDGET_STATE";

    public static final String PARM_STATUS = "status";
    public static final String PARM_WIDGET_ID = "widget_id";

    public static final String STATUS_DISABLED = "DISABLED";
    public static final String STATUS_CONNECTING = "CONNECTING";
    public static final String STATUS_CONNECTED = "CONNECTED";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        if( appWidgetIds != null && appWidgetIds.length > 0) {
            this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        PreferenceHelper.setContext(context);
        for( int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
        final RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget_1x2);

        WidgetStatusPreference preference = new WidgetStatusPreference();
        String status = preference.getAction(appWidgetId);

        Log.i("widget", "status : " + status);
        switch (status) {
            case STATUS_DISABLED:
                updateViews.setImageViewResource(R.id.btn_left, R.drawable.ic_sun_disabled);
                updateViews.setImageViewResource(R.id.btn_right, R.drawable.ic_moon_disabled);
                break;
            case STATUS_CONNECTING:
                startAnimation(updateViews, appWidgetManager, appWidgetId);
                break;
            case STATUS_CONNECTED:
                stopAnimation(updateViews, appWidgetManager, appWidgetId);
                updateViews.setImageViewResource(R.id.btn_left, R.drawable.selector_sun_connected);
                updateViews.setImageViewResource(R.id.btn_right, R.drawable.selector_moon_connected);
                break;
        }

        Intent intent1 = new Intent(context, WidgetService.class);
        Intent intent2 = new Intent(context, WidgetService.class);
        intent1.putExtra(PARM_WIDGET_ID, appWidgetId);
        intent2.putExtra(PARM_WIDGET_ID, appWidgetId);
        PendingIntent pendingIntent1 = PendingIntent.getService(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getService(context, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.btn_left, pendingIntent1);
        updateViews.setOnClickPendingIntent(R.id.btn_right, pendingIntent2);

        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
        Log.i("widget", "update app widget (id:" + appWidgetId + ")");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        PreferenceHelper.setContext(context);
        WidgetStatusPreference preference = new WidgetStatusPreference();
        for (int id : appWidgetIds) {
            preference.setAction(id, MyWidget1x2.STATUS_DISABLED);
        }
    }

    boolean animationFlag = false;
    int blink = 0;
    Handler handler = new Handler(Looper.getMainLooper());
    List<Integer> images;

    public void startAnimation(RemoteViews remoteViews, AppWidgetManager manager, int appWidgetId)
    {
        Log.i("widget", "start");
        blink = 0;
        animationFlag = true;
        images = new ArrayList<>();
        images.add(R.drawable.ic_moon);
        images.add(R.drawable.ic_sun);
        if(!handler.hasMessages(0))
            handler.postDelayed(getAnimateRunnable(remoteViews, manager, appWidgetId), 1000);
    }

    public void stopAnimation(RemoteViews remoteViews, AppWidgetManager manager, int appWidgetId)
    {
        Log.i("widget", "Stop Animation Before:"+animationFlag);
        animationFlag = false;
        Log.i("widget", "Stop Animation:After:"+animationFlag);
        if(!handler.hasMessages(0)) {
            Log.i("widget", "handler.hasmessage0");
            handler.removeCallbacks(getAnimateRunnable(remoteViews, manager, appWidgetId));
        }
    }

    private Runnable getAnimateRunnable(final RemoteViews remoteViews, final AppWidgetManager manager, final int appWidgetId) {
        return new Runnable() {
            @Override
            public void run() {
                if (!animationFlag)
                    return;
                blink = ++blink%2;
                Log.i("widget", "blink" + blink);

                remoteViews.setImageViewResource(R.id.btn_left, images.get(blink));
                remoteViews.setImageViewResource(R.id.btn_right, images.get(blink));

                manager.updateAppWidget(appWidgetId, remoteViews);

                handler.postDelayed(getAnimateRunnable(remoteViews, manager, appWidgetId), 1000);
            }
        };
    }
}
