package info.kingpes.fcm.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.WindowManager;
import android.widget.RemoteViews;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

import info.kingpes.fcm.MainActivity;
import info.kingpes.fcm.NotifyActivity;
import info.kingpes.fcm.R;

/**
 * Created by Chau Huynh on 7/24/2018.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        startService(new Intent(this, IncomingService.class));
        wakeup();

//        if (remoteMessage.getNotification() != null) {
//            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//        }
//
//        if (remoteMessage.getData().size() > 0) {
//            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
//        }

    }

    private void showNotification(String title, String message) {
        Intent intent = new Intent(this, NotifyActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        Intent intent1 = new Intent(this, NotifyActivity.class);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent1);

        //Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.ringing);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel1")
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(soundUri)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                //.setContentIntent(fullScreenPendingIntent)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(true)
                .setShowWhen(true)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_HIGH)
                .setCustomHeadsUpContentView(getCustomDesign("Xin Chao", "Hello"));


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel1",
                    "test",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);

        }

        int id = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(id, builder.build());

        wakeup();

    }

    private void wakeup(){
        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = Build.VERSION.SDK_INT >= 20 ? pm.isInteractive() : pm.isScreenOn(); // check if screen is on
        if (!isScreenOn) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myApp:notificationLock");
            wl.acquire(3000); //set your time in milliseconds
        }
    }

    private RemoteViews getCustomDesign(String title, String message) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.btn, title);
        //remoteViews.setTextViewText(R.id.sip_uri, message);
        //remoteViews.setTextViewText(R.id.incoming_call_info, getString(R.string.app_name));
        return remoteViews;
    }

}
