package com.erenutku.broadcastwidgetexample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BroadcastWidget extends AppWidgetProvider {
    private static final String ACTION_SIMPLEAPPWIDGET = "ACTION_BROADCASTWIDGETSAMPLE";
    private static int mCounter = 0;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.broadcast_widget);
        // Construct an Intent which is pointing this class.
        Intent intent = new Intent(context, BroadcastWidget.class);
        intent.setAction(ACTION_SIMPLEAPPWIDGET);
        // And this time we are sending a broadcast with getBroadcast
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_SIMPLEAPPWIDGET.equals(intent.getAction())) {
            mCounter++;
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.broadcast_widget);
            views.setTextViewText(R.id.tvWidget, Integer.toString(mCounter));
            // This time we dont have widgetId. Reaching our widget with that way.
            ComponentName appWidget = new ComponentName(context, BroadcastWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidget, views);
        }
    }

}

