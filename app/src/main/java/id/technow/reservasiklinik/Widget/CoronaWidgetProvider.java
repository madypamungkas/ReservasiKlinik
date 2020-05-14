package id.technow.reservasiklinik.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import id.technow.reservasiklinik.MainActivity;
import id.technow.reservasiklinik.Model.CoronaWidget;
import id.technow.reservasiklinik.R;
import id.technow.reservasiklinik.Storage.SharedPrefManager;

public class CoronaWidgetProvider extends AppWidgetProvider {
    Context mCtx;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            CoronaWidget coronaWidget = SharedPrefManager.getInstance(context).getCorona();
            views.setTextViewText(R.id.jml_positifIndo, String.valueOf(coronaWidget.indPos));
            views.setTextViewText(R.id.jml_sembuhIndo, String.valueOf(coronaWidget.indSembuh));
            views.setTextViewText(R.id.jml_meninggalIndo, String.valueOf(coronaWidget.indMen));
             views.setTextViewText(R.id.jml_positif, String.valueOf(coronaWidget.provPos));
            views.setTextViewText(R.id.jml_sembuh, String.valueOf(coronaWidget.provSembuh));
            views.setTextViewText(R.id.jml_meninggal, String.valueOf(coronaWidget.provMen));
            views.setTextViewText(R.id.city, String.valueOf(coronaWidget.provinsi));


         /*   views.setOnClickPendingIntent(R.id.location,loadCorona());
           */
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
