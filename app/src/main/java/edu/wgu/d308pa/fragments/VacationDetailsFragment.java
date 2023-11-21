package edu.wgu.d308pa.fragments;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.wgu.d308pa.R;
import edu.wgu.d308pa.dao.ExcursionDao;
import edu.wgu.d308pa.dao.VacationDao;
import edu.wgu.d308pa.entities.Vacation;

public class VacationDetailsFragment extends Fragment {

    private VacationDao vacationDao;
    private ExcursionDao excursionDao;
    private Vacation retrievedVacation;
    private TextView title;
    private TextView hotel;
    private TextView start;
    private TextView end;


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
    }
}
