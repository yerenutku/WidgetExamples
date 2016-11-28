package com.erenutku.configurablewidgetexample;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;

/**
 * The configuration screen for the {@link ConfigurableWidget ConfigurableWidget} AppWidget.
 */
public class ConfigurableWidgetConfigureActivity extends Activity {
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText etUrl;
    private AppWidgetManager widgetManager;
    private RemoteViews views;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.configurable_widget_configure);
        etUrl = (EditText) findViewById(R.id.etUrl);
        widgetManager = AppWidgetManager.getInstance(this);
        views = new RemoteViews(this.getPackageName(),
                R.layout.configurable_widget);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        findViewById(R.id.btAdd).setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(etUrl.getText().toString()));
            PendingIntent pending =
                    PendingIntent.getActivity(ConfigurableWidgetConfigureActivity.this, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.ivWidget, pending);
            widgetManager.updateAppWidget(mAppWidgetId, views);
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };
}

