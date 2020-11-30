package info.kingpes.fcm.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import info.kingpes.fcm.BuildConfig;
import info.kingpes.fcm.NotifyActivity;
import info.kingpes.fcm.R;

public class FirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        showNotification();
    }

    private void showNotification() {

        Intent notificationIntent = new Intent(this, NotifyActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel("InComingCall", "InComingCall", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(null, null);

            notificationManager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "InComingCall")
                    .setContentTitle(BuildConfig.APPLICATION_ID)
                    .setTicker("Call_STATUS")
                    .setContentText("InComingCall")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setDefaults(NotificationCompat.DEFAULT_LIGHTS | NotificationCompat.DEFAULT_SOUND)
                    .setCategory(NotificationCompat.CATEGORY_CALL)
                    .setVibrate(null)
                    .setOngoing(true)
                    .setFullScreenIntent(pendingIntent, true)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(remoteViews)
                    .setCustomBigContentView(remoteViews);

            startForeground(1124, notification.build());
        } else {

            NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "")
                    .setContentTitle(BuildConfig.APPLICATION_ID)
                    .setTicker("Call_STATUS")
                    .setContentText("InComingCall")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                    .setDefaults(NotificationCompat.DEFAULT_LIGHTS | NotificationCompat.DEFAULT_SOUND)
                    .setVibrate(null)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .setCategory(NotificationCompat.CATEGORY_CALL)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .addAction(new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "HANG UP", pendingIntent).build());


                startForeground(1124, notification.build());

        }
    }
}
