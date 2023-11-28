package edu.wgu.d308pa.services;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import edu.wgu.d308pa.R;

public class AlarmReceiver extends BroadcastReceiver {
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Hit onReceive");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "vacNot")
                .setSmallIcon(R.drawable.beach)
                .setContentTitle("Vacation Alarm")
                .setContentText("Need to add vacation title and whether start or end")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = notificationBuilder.build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1000001, notification);
    }
}
