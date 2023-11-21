package edu.wgu.d308pa.fragments;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.wgu.d308pa.R;
import edu.wgu.d308pa.dao.VacationDao;
import edu.wgu.d308pa.entities.Vacation;

public class AddEditVacationFragment extends Fragment {

    public static boolean fromEdit = false;
    private TextView textView;
    private EditText title;
    private EditText hotel;
    private EditText start;
    private EditText end;
    private VacationDao vacationDao;
    private Button saveButton;
    Vacation retrievedVacation;

    public AddEditVacationFragment() {
        super(R.layout.add_edit_vacation_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        textView = view.findViewById(R.id.add_or_edit_title_textview);
        title = view.findViewById(R.id.add_or_edit_title_edittext);
        hotel = view.findViewById(R.id.add_or_edit_hotel_edittext);
        start = view.findViewById(R.id.add_or_edit_start_edittext);
        end = view.findViewById(R.id.add_or_edit_end_edittext);
        saveButton = view.findViewById(R.id.add_or_edit_save_button);
        vacationDao = VacationFragment.vacationDao;

        if (fromEdit) {
            retrievedVacation = vacationDao.findById(VacationFragment.VacationIdFromLongClick);
            textView.setText("Edit your vacation");
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vacation vacation = new Vacation();
                vacation.setTitle(String.valueOf(title.getText()));
                vacation.setHotel(String.valueOf(hotel.getText()));

                String startString = String.valueOf(start.getText());
                SimpleDateFormat startFormat = new SimpleDateFormat("dd/MM/yyyy");

                String endString = String.valueOf(end.getText());
                SimpleDateFormat endFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    vacation.setStart(startFormat.parse(startString).getTime());
                    vacation.setEnd(endFormat.parse(endString).getTime());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                if (fromEdit) {
                    vacation.setVacationId(retrievedVacation.getVacationId());
                    vacationDao.update(vacation);
                    fromEdit = false;
                }
                else {
                    vacationDao.insert(vacation);
                }

                // this pops the current fragment starting the next in line
                getParentFragmentManager().popBackStack();
            }
        });
    }
}
