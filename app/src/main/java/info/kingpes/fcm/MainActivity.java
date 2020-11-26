package info.kingpes.fcm;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import info.kingpes.fcm.Remote.APIService;
import info.kingpes.fcm.model.MyResponse;
import info.kingpes.fcm.model.Notification;
import info.kingpes.fcm.model.Sender;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*Mail: chau7283@gmail.com
* Add new project
* Select Platform
* get SHA-1: android/sign report
* Copy json to: D:\Sample\FCM\app
* */

public class MainActivity extends AppCompatActivity {

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                if (!TextUtils.isEmpty(token)) {
                    Log.d("MyToken",token);
                } else {
                    Log.d("MyToken", "null");
                }
            }
        });


        //For Multi
        FirebaseMessaging.getInstance().subscribeToTopic("Kingpes");

        apiService = Common.getFCMClient();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification notification = new Notification("Title", "Body");

                //Sender sender = new Sender(Common.currentToken, notification);

                //For Multi
                Sender sender = new Sender("/topics/Kingpes", notification);

                apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        if (response.body().success == 1) {
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            }
        });

    }
}
