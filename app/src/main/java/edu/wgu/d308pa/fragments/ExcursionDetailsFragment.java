package edu.wgu.d308pa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.wgu.d308pa.R;
import edu.wgu.d308pa.dao.ExcursionDao;
import edu.wgu.d308pa.entities.Excursion;

public class ExcursionDetailsFragment extends Fragment {

    Bundle receivedBundle;
    ExcursionDao excursionDao;
    Excursion retrievedExcursion;
    TextView title, start;
    Button edit, share, delete;
    Fragment addEditFragment;

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

        // populate the fields
        title.setText(retrievedExcursion.getTitle());
        // TODO: This date conversion code is repeated everywhere
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
    }
}
