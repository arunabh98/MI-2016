package com.iitb.moodindigo.mi2016;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NotificationIntentService extends IntentService {

    private static int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public NotificationIntentService() {
        super(NotificationIntentService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                processStartNotification();
            }
            if (ACTION_DELETE.equals(action)) {
                processDeleteNotification(intent);
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    private void processStartNotification() {
        // Do something. For example, fetch fresh data from backend to create a rich notification?

        SharedPreferences goingPreferences = getApplicationContext().getSharedPreferences("GOING", Context.MODE_PRIVATE);
        String goingList = goingPreferences.getString("GOING_LIST", null);
        Type type = new TypeToken<List<GsonModels.Event>>() {
        }.getType();
        List<GsonModels.Event> goingListGson = (new Gson()).fromJson(goingList, type);
        if (goingListGson == null) {
            ;
        } else {
            for (int i = 0; i < goingListGson.size(); i++) {
                GsonModels.Event event = goingListGson.get(i);
                Log.d("nihal111", "Minutes left: " + getDateDiff(new Date(), event.getDate(), TimeUnit.MINUTES));
                if (getDateDiff(new Date(), event.getDate(), TimeUnit.MINUTES) <= 15) { //Change this to 15*10000 for testing
                    NOTIFICATION_ID = (int) Long.parseLong(event.get_id().substring(6,11), 16);
                    Log.d("nihal111", "notification ID= " + NOTIFICATION_ID);
                    final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                    builder.setContentTitle("Mood Indigo: " + event.getTitle())
                            .setAutoCancel(true)
                            .setColor(getResources().getColor(R.color.colorAccent))
                            .setContentText("Event is about to start in " + getDateDiff(new Date(), event.getDate(), TimeUnit.MINUTES) + " minutes.")
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notification_logo))
                            .setSmallIcon(R.drawable.notification_logo);

                    PendingIntent pendingIntent = PendingIntent.getActivity(this,
                            NOTIFICATION_ID,
                            new Intent(this, MainActivity.class),
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);
                    builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

                    final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(NOTIFICATION_ID, builder.build());
                }
            }
        }
    }
}