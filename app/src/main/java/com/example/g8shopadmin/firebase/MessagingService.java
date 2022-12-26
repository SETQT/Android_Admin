package com.example.g8shopadmin.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.ChatActivity;
import com.example.g8shopadmin.activities.activity_admin_order;
import com.example.g8shopadmin.models.UserChat;
import com.example.g8shopadmin.utilities.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MessagingService extends FirebaseMessagingService {


    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String channelId = "", title = "", body = "";
        int notificationId = new Random().nextInt();
        PendingIntent pendingIntent = null;

        if (remoteMessage.getNotification().getTag().equals("USER_ORDER")) {
            channelId = "notification_user_to_admin";
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();

            Intent intent = new Intent(this, activity_admin_order.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(Constants.STATE_ORDER, "1");
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        } else {
            UserChat user = new UserChat();
            user.id = remoteMessage.getData().get(Constants.KEY_USER_ID);
            user.fullName = remoteMessage.getData().get("name");
            user.token = remoteMessage.getData().get(Constants.KEY_FCM_TOKEN);

            title = user.fullName;
            body = remoteMessage.getData().get(Constants.KEY_MESSAGE);
            channelId = "chat_message";

            Intent intent = new Intent(this, ChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(Constants.KEY_USER, user);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(remoteMessage.getData().get(Constants.KEY_MESSAGE));
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(
                body
        ));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = channelId;
            String channelDescription = "This notification channel is used for chat message notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, builder.build());

    }
}
