package edu.wgu.d308pa.services;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.Objects;
import edu.wgu.d308pa.R;

public class AlarmReceiver extends BroadcastReceiver {
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "vacNot")
                .setSmallIcon(R.drawable.beach)
                .setContentTitle(intent.getStringExtra("title"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Objects.equals(intent.getStringExtra("Start"), "true")) {
            notificationBuilder.setContentText("Your vacation or excursion will be starting soon!");
        }
        else { notificationBuilder.setContentText("Your vacation will be ending soon!"); }

        Notification notification = notificationBuilder.build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(99999, notification);
    }
}
