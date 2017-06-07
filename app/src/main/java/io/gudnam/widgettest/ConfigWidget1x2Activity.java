package io.gudnam.widgettest;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import io.gudnam.widgettest.adapter.WidgetConfigAdapter;

/**
 * Created by gudnam on 2016. 1. 14..
 */
public class ConfigWidget1x2Activity extends AppCompatActivity implements WidgetConfigAdapter.OnItemClickListener {
    private static final String TAG = ConfigWidget1x2Activity.class.getSimpleName();


    private int widgetId;
    private AppWidgetManager appWidgetManager;
    private RemoteViews remoteView;

    private WidgetConfigAdapter adapter;

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_config);

        Bundle mExtras = getIntent().getExtras();
        if (mExtras != null) {
            widgetId = mExtras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        appWidgetManager = AppWidgetManager.getInstance(this);
        remoteView = new RemoteViews(this.getPackageName(),
                R.layout.layout_widget_1x2);

        adapter = new WidgetConfigAdapter(getItems(), this);

        initRecyclerListView();
    }

    private void initRecyclerListView() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_device_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

    }

    private List<String> getItems() {
        list = new ArrayList<>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test4");
        list.add("test5");
        return list;
    }

    @Override
    public void onItemClick(int i) {
        appWidgetManager.updateAppWidget(widgetId, remoteView);

        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, intent);

        finish();
    }
}
