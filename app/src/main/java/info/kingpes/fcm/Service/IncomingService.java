package info.kingpes.fcm.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;


import info.kingpes.fcm.R;

public class IncomingService extends Service {
    WindowManager manager;
    View view;
    private MediaPlayer mediaPlayer;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        view = LayoutInflater.from(this).inflate(R.layout.notification, null);

        int FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
                PixelFormat.TRANSLUCENT
        );

        manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        if (manager != null)
            manager.addView(view, params);

        mediaPlayer = MediaPlayer.create(this, R.raw.ringing);
        mediaPlayer.start();

        Button img = view.findViewById(R.id.btn);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                stopSelf();
            }
        });

        vibrator();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (manager != null)
            manager.removeView(view);
    }

    private void vibrator(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }

}
