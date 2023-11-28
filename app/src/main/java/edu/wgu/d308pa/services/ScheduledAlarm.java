package edu.wgu.d308pa.services;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

public class ScheduledAlarm {
    private Context context;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static int requestCode = 1000000;

    public ScheduledAlarm(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(Calendar calendar, String title, boolean start) {
        long triggerTime = calendar.getTimeInMillis();
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("start", String.valueOf(start));
        pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        requestCode++;
    }

    public void cancelAlarm() {
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
