package com.example.homeautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class splashscreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private static final String SUBSCRIBE_TO = MainActivity.SUBSCRIBE_TO;
    private static final String CHANNEL_ID = MainActivity.CHANNEL_ID;
    private static final String IMAGE_TOPIC = MainActivity.IMAGE_TOPIC;


    public void createNotificationChannel(){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "desc", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("fcm notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
//                    Toast.makeText(splashscreen.this, task.getResult().getId(), Toast.LENGTH_SHORT).show();
                    Log.d("FCM", "onComplete: token " + task.getResult().getToken());
                    Log.d("FCM", "onComplete: instanceid " + task.getResult().getId());
                    String token = task.getResult().getToken();
                }
            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO);
        FirebaseMessaging.getInstance().subscribeToTopic(IMAGE_TOPIC);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
