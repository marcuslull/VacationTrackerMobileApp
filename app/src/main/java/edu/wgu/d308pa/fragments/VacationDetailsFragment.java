package edu.wgu.d308pa.fragments;

import android.annotation.SuppressLint;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
    private Button edit, delete, share;
    private Switch notification;
    private int permissionRequestId = 1;
    private String friendlyStringStart, friendlyStringEnd;
    FragmentManager fragmentManager;
    Fragment excursionFragment;


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
        share = view.findViewById(R.id.vacation_details_share_button);

        //TODO: Refactor - duplicated code from add/edit vacation fragment
        retrievedVacation = vacationDao.findById(VacationFragment.VacationIdFromLongClick);
        title.setText(retrievedVacation.getTitle());
        hotel.setText(retrievedVacation.getHotel());

        Long startLong = retrievedVacation.getStart();
        SimpleDateFormat startFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = new Date(startLong);
        start.setText(startFormat.format(startDate));
        friendlyStringStart = startFormat.format(startDate);

        Long endLong = retrievedVacation.getEnd();
        SimpleDateFormat endFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date endDate = new Date(endLong);
        end.setText(endFormat.format(endDate));
        friendlyStringEnd = endFormat.format(endDate);

        // adding the excursion fragment
        Bundle bundle = new Bundle();
        bundle.putLong("vacationId", retrievedVacation.getVacationId());
        fragmentManager = getParentFragmentManager();
        excursionFragment = new ExcursionFragment();
        excursionFragment.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.vacation_details_fragment_container_view, excursionFragment);
        transaction.commit();

        // set the static vacationId on deleteAlert so it knows what excursions to refresh after deletion
        DeleteAlertDialogFragment.lastVacationId = retrievedVacation.getVacationId();

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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vacationDetails = "Check out my vacation details: " +
                        retrievedVacation.getTitle() + "\nWe are staying at " +
                        retrievedVacation.getHotel() + " starting " +
                        friendlyStringStart + " ending " +
                        friendlyStringEnd + ".";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, vacationDetails);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
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
                        return; // Don't want this option to move on
                    }
                    // permission has been granted already
                    setAlarms();
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
                setAlarms();
            }
            else {
                if (permissionRequestId > 1) {
                    // permission has been denied already, user must manually enable permission
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                    startActivity(intent);
                    notification.toggle();
                }
                // user denied permission
                permissionRequestId += 1;
                notification.toggle();
            }
        }
    }

    public void setAlarms() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(retrievedVacation.getStart());
        ScheduledAlarm startAlarm = new ScheduledAlarm(getContext());
        startAlarm.setAlarm(startCalendar, retrievedVacation.getTitle(), true);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(retrievedVacation.getEnd());
        ScheduledAlarm endAlarm = new ScheduledAlarm(getContext());
        endAlarm.setAlarm(endCalendar, retrievedVacation.getTitle(), false);
    }
}
