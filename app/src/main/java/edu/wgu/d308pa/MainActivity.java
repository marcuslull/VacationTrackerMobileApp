package edu.wgu.d308pa;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import edu.wgu.d308pa.fragments.VacationFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // create back button on the action bar
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            // transfer data to the fragment example
            Bundle bundle = new Bundle();
            bundle.putInt("some_int", 0);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    // sets the fragment to display programmatically
                    .add(R.id.fragmentContainerView, VacationFragment.class, bundle) // bundle can be null if there is no data to transfer
                    .commit();
        }

        // create the notification channel for the vacation/excursion alerts
        setNotificationChannel();
    }

    // create and register a new Notification channel for vacation notifications.
    private void setNotificationChannel() {
        CharSequence name = "Vacation Notification";
        String description = "Channel for vacation notifications";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("vacNot", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    // for the back button, needs to know top fragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainerView);
            // if this is the top frag then hand off to super
            if (fragment instanceof VacationFragment) {
                super.onBackPressed();
                return true;
            }
            // otherwise pop the view stack
            else {
                fragmentManager.popBackStack();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}