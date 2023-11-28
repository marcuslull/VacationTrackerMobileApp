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

    public ScheduledAlarm(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(Calendar calendar) {
        System.out.println("Hit set alarm");
        long triggerTime = calendar.getTimeInMillis();
        System.out.println("Now: " + Calendar.getInstance().getTimeInMillis() + " Trigger: " + triggerTime);
        Intent intent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 1000001, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
    }

    public void cancelAlarm() {
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
