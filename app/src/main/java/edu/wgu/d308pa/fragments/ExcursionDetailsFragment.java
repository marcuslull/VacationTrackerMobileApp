package edu.wgu.d308pa.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.wgu.d308pa.R;
import edu.wgu.d308pa.dao.ExcursionDao;
import edu.wgu.d308pa.entities.Excursion;
import edu.wgu.d308pa.services.ScheduledAlarm;

public class ExcursionDetailsFragment extends Fragment {

    Bundle receivedBundle;
    ExcursionDao excursionDao;
    Excursion retrievedExcursion;
    TextView title, start;
    Button edit, share, delete;
    Fragment addEditFragment;
    Switch notification;
    private int permissionRequestId = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        excursionDao = VacationFragment.excursionDao;
        return inflater.inflate(R.layout.excursion_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        // get all the resources
        receivedBundle = getArguments();
        retrievedExcursion = excursionDao.findById(receivedBundle.getLong("excursionId"));
        title = view.findViewById(R.id.excursion_details_title_textview);
        start = view.findViewById(R.id.excursion_details_start_textview);
        edit = view.findViewById(R.id.excursion_details_edit_button);
        share = view.findViewById(R.id.excursion_details_share_button);
        delete = view.findViewById(R.id.excursion_details_delete_button);
        notification = view.findViewById(R.id.excursion_details_notification_switch);

        // populate the fields
        title.setText(retrievedExcursion.getTitle());
        Long startLong = retrievedExcursion.getDate();
        SimpleDateFormat startFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = new Date(startLong);
        start.setText(startFormat.format(startDate));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditFragment = new AddEditExcursionFragment();
                addEditFragment.setArguments(receivedBundle);
                AddEditExcursionFragment.isEdit = true;
                getParentFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.vacation_details_fragment_container_view, addEditFragment, null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAlertDialogFragment.isFromExcursionDetails = true;
                DeleteAlertDialogFragment.lastExcursionId = retrievedExcursion.excursionId;
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
        startCalendar.setTimeInMillis(retrievedExcursion.getDate());
        ScheduledAlarm startAlarm = new ScheduledAlarm(getContext());
        startAlarm.setAlarm(startCalendar, retrievedExcursion.getTitle(), true);
    }
}
