package edu.wgu.d308pa.fragments;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import edu.wgu.d308pa.R;
import edu.wgu.d308pa.dao.ExcursionDao;
import edu.wgu.d308pa.dao.VacationDao;
import edu.wgu.d308pa.entities.Vacation;
import edu.wgu.d308pa.services.ScheduledAlarm;

public class VacationDetailsFragment extends Fragment {

    private VacationDao vacationDao;
    private ExcursionDao excursionDao;
    private Vacation retrievedVacation;
    private TextView title, hotel, start, end;
    private Button edit, delete;
    private Switch notification;
    private int permissionRequestId = 1;
    NotificationCompat.Builder builder;
    PendingIntent pendingIntent;

    public VacationDetailsFragment() {
        super(R.layout.vacation_details_fragment);
        vacationDao = VacationFragment.vacationDao;
        excursionDao = VacationFragment.excursionDao;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        title = view.findViewById(R.id.vacation_details_title_textview);
        hotel = view.findViewById(R.id.vacation_details_hotel_textview);
        start = view.findViewById(R.id.vacation_details_start_textview);
        end = view.findViewById(R.id.vacation_details_end_textview);
        edit = view.findViewById(R.id.vacation_details_edit_button);
        delete = view.findViewById(R.id.vacation_details_delete_button);
        notification = view.findViewById(R.id.vacation_details_notification_switch);

        //TODO: Refactor - duplicated code from add/edit vacation fragment
        retrievedVacation = vacationDao.findById(VacationFragment.VacationIdFromLongClick);
        title.setText(retrievedVacation.getTitle());
        hotel.setText(retrievedVacation.getHotel());

        Long startLong = retrievedVacation.getStart();
        SimpleDateFormat startFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = new Date(startLong);
        start.setText(startFormat.format(startDate));

        Long endLong = retrievedVacation.getEnd();
        SimpleDateFormat endFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date endDate = new Date(endLong);
        end.setText(endFormat.format(endDate));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditVacationFragment.fromEdit = true;
                getParentFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainerView, AddEditVacationFragment.class, null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAlertDialogFragment.fromDetails = true;
                new DeleteAlertDialogFragment().show(getParentFragmentManager(), null);
            }
        });

        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            "android.permission.POST_NOTIFICATIONS")
                            != PackageManager.PERMISSION_GRANTED){
                        // permission has not been granted yet
                        requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, permissionRequestId);
                        return;
                    }
                    // permission has been granted already
                    System.out.println("permission has been granted already");
                    ScheduledAlarm scheduledAlarm = new ScheduledAlarm(getContext());
                    long fiveSecsFromNow = Calendar.getInstance().getTimeInMillis() + 5000;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(fiveSecsFromNow);
                    scheduledAlarm.setAlarm(calendar);
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]  grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionRequestId) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // this is the first time permission has been granted
                System.out.println("this is the first time permission has been granted");
                ScheduledAlarm scheduledAlarm = new ScheduledAlarm(getContext());
                long fiveSecsFromNow = Calendar.getInstance().getTimeInMillis() + 5000;
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(fiveSecsFromNow);
                scheduledAlarm.setAlarm(calendar);
            }
            else {
                if (permissionRequestId > 1) {
                    // permission has been denied already, user must manually enable permission
                    System.out.println("permission has been denied already, user must manually enable permission");
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                    startActivity(intent);
                    notification.toggle();
                }
                // user denied permission
                System.out.println("user denied permission");
                permissionRequestId += 1;
                notification.toggle();
            }
        }
    }
}
