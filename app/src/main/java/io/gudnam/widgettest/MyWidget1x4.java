package io.gudnam.widgettest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import io.gudnam.widgettest.preference.PreferenceHelper;
import io.gudnam.widgettest.preference.WidgetStatusPreference;
import io.gudnam.widgettest.service.WidgetService;

import static io.gudnam.widgettest.MyWidget1x2.PARM_WIDGET_ID;
import static io.gudnam.widgettest.MyWidget1x2.STATUS_CONNECTED;
import static io.gudnam.widgettest.MyWidget1x2.STATUS_CONNECTING;
import static io.gudnam.widgettest.MyWidget1x2.STATUS_DISABLED;

/**
 * Created by gudnam on 2017. 5. 22..
 */

public class MyWidget1x4 extends AppWidgetProvider {

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
        final RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget_1x4);

        WidgetStatusPreference preference = new WidgetStatusPreference();
        String status = preference.getAction(appWidgetId);

        Log.i("widget", "status : " + status);
        updateViews.setViewVisibility(R.id.pb_connecting, View.GONE);
        switch (status) {
            case STATUS_DISABLED:
                updateViews.setImageViewResource(R.id.btn_left, R.drawable.ic_sun_disabled);
                updateViews.setImageViewResource(R.id.btn_right, R.drawable.ic_moon_disabled);
                updateViews.setImageViewResource(R.id.btn_left2, R.drawable.ic_sun_disabled);
                updateViews.setImageViewResource(R.id.btn_right2, R.drawable.ic_moon_disabled);
                break;
            case STATUS_CONNECTING:
                updateViews.setViewVisibility(R.id.pb_connecting, View.VISIBLE);
                break;
            case STATUS_CONNECTED:
                updateViews.setImageViewResource(R.id.btn_left, R.drawable.selector_sun_connected);
                updateViews.setImageViewResource(R.id.btn_right, R.drawable.selector_moon_connected);
                updateViews.setImageViewResource(R.id.btn_left2, R.drawable.selector_sun_connected);
                updateViews.setImageViewResource(R.id.btn_right2, R.drawable.selector_moon_connected);
                break;
        }

        Intent intent1 = new Intent(context, WidgetService.class);
        Intent intent2 = new Intent(context, WidgetService.class);
        Intent intent3 = new Intent(context, WidgetService.class);
        Intent intent4 = new Intent(context, WidgetService.class);
        intent1.putExtra(PARM_WIDGET_ID, appWidgetId);
        intent2.putExtra(PARM_WIDGET_ID, appWidgetId);
        intent3.putExtra(PARM_WIDGET_ID, appWidgetId);
        intent4.putExtra(PARM_WIDGET_ID, appWidgetId);
        PendingIntent pendingIntent1 = PendingIntent.getService(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getService(context, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent3 = PendingIntent.getService(context, 2, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent4 = PendingIntent.getService(context, 3, intent4, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.btn_left, pendingIntent1);
        updateViews.setOnClickPendingIntent(R.id.btn_right, pendingIntent2);
        updateViews.setOnClickPendingIntent(R.id.btn_left2, pendingIntent3);
        updateViews.setOnClickPendingIntent(R.id.btn_right2, pendingIntent4);

        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
        Log.i("widget", "update app widget (id:" + appWidgetId + ")");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        PreferenceHelper.setContext(context);
        WidgetStatusPreference preference = new WidgetStatusPreference();
        for (int id : appWidgetIds) {
            preference.setAction(id, STATUS_DISABLED);
        }
    }
}
