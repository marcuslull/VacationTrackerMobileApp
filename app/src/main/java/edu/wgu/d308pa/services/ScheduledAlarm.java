package edu.wgu.d308pa.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Random;

public class ScheduledAlarm {
    private Context context;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int requestCode;

    public ScheduledAlarm(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    // We need a unique request code so the receiver knows the correct intent extras to grab
    private void setRandomRequestCode(){
        Random random = new Random();
        requestCode = random.nextInt();
        System.out.println(requestCode);
    }

    public void setAlarm(Calendar calendar, String title, boolean start) {
        setRandomRequestCode(); // bug fix: Notifications were using the same requestCode
        long triggerTime = calendar.getTimeInMillis();
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("start", String.valueOf(start));
        pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
    }

    public void cancelAlarm() {
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
