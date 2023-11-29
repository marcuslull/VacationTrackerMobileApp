package edu.wgu.d308pa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        receivedBundle = getArguments();
        retrievedExcursion = excursionDao.findById(receivedBundle.getLong("excursionId"));
        title = view.findViewById(R.id.excursion_details_title_textview);
        start = view.findViewById(R.id.excursion_details_start_textview);
        title.setText(retrievedExcursion.getTitle());
        // TODO: This date conversion code is repeated everywhere
        Long startLong = retrievedExcursion.getDate();
        SimpleDateFormat startFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = new Date(startLong);
        start.setText(startFormat.format(startDate));
    }
}
