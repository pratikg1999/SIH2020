package com.example.homeautomation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CloudMessageService extends FirebaseMessagingService {
    public CloudMessageService() {
    }

    private static final String CHANNEL_ID = MainActivity.CHANNEL_ID;
    private static final String TAG = "mFirebaseIIDService";
    private static final String SUBSCRIBE_TO = MainActivity.SUBSCRIBE_TO;
    private static final String IMAGE_TOPIC = MainActivity.IMAGE_TOPIC;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("cloud messaging", "From: " + remoteMessage.getFrom());
//        Toast.makeText(getApplicationContext(), "got "+ remoteMessage.toString(), Toast.LENGTH_SHORT).show();
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

        Log.d("isNotification", (remoteMessage.getNotification()!=null)+"");
        if (remoteMessage.getNotification() != null) {
            Log.d("cloud messaging", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d("title", "onMessageReceived: " + remoteMessage.getData().get("title"));
            Log.d("message", "onMessageReceived: " + remoteMessage.getData().get("message"));
            Log.d("data", "onMessageReceived: " + remoteMessage.getData().get("message"));

        }

        NotificationHelper.dispNotification(this, "kjsad;lkfj", remoteMessage.getData().get("message"));
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d("firebase messaging", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        // Once the token is generated, subscribe to topic with the userId
        FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO);
        FirebaseMessaging.getInstance().subscribeToTopic(IMAGE_TOPIC);
        Log.i(TAG, "onTokenRefresh completed with token: " + token);
    }

}
