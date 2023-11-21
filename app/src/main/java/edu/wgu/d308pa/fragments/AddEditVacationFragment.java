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
    private EditText title, hotel, start, end;
    private VacationDao vacationDao;
    private Button saveButton;
    private Vacation retrievedVacation, addedVacation;

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

                if (validInput()) {

                    addedVacation = new Vacation();
                    addedVacation.setTitle(String.valueOf(title.getText()));
                    addedVacation.setHotel(String.valueOf(hotel.getText()));

                    String startString = String.valueOf(start.getText());
                    SimpleDateFormat startFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String endString = String.valueOf(end.getText());
                    SimpleDateFormat endFormat = new SimpleDateFormat("dd/MM/yyyy");

                    try {
                        addedVacation.setStart(startFormat.parse(startString).getTime());
                        addedVacation.setEnd(endFormat.parse(endString).getTime());
                    }
                    catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    if (fromEdit) {
                        addedVacation.setVacationId(retrievedVacation.getVacationId());
                        vacationDao.update(addedVacation);
                        fromEdit = false;
                    }
                    else {
                        vacationDao.insert(addedVacation);
                    }

                    // this pops the current fragment starting the next in line
                    getParentFragmentManager().popBackStack();
                }
            }
        });
    }

    private boolean validInput() {
        if (title.getText().length() == 0) {
            title.setError("Please specify a vacation name");
            return false;
        }
        if (hotel.getText().length() == 0) {
            hotel.setError("Please specify a hotel name or destination");
            return false;
        }
        if (start.getText().length() == 0) {
            start.setError("Please set a start date");
            return false;
        }
        if (end.getText().length() == 0) {
            end.setError("Please set an end date");
            return false;
        }
        if (!dateFormattedCorrectly(start)) {
            start.setError("Use the format dd/mm/yyyy");
            return false;
        }
        if (!dateFormattedCorrectly(end)) {
            end.setError("Use the format dd/mm/yyyy");
            return false;
        }
        if (!datesInCorrectOrder(start, end)) {
            start.setError("Start date must be before end date");
            end.setError("Start date must be before end date");
            return false;
        }
        return true;
    }

    private boolean dateFormattedCorrectly(EditText date) {
        try {
            String string = String.valueOf(date.getText());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.parse(string);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean datesInCorrectOrder(EditText start, EditText end) {
        //TODO: refactor all the repeated code.
        String startString = String.valueOf(start.getText());
        SimpleDateFormat startFormat = new SimpleDateFormat("dd/MM/yyyy");

        String endString = String.valueOf(end.getText());
        SimpleDateFormat endFormat = new SimpleDateFormat("dd/MM/yyyy");

        Long startLong;
        Long endLong;

        try {
            startLong = startFormat.parse(startString).getTime();
            endLong = endFormat.parse(endString).getTime();
            if (startLong < endLong ) {
                return true;
            }
        }
        catch (ParseException e) {
            return false;
        }
        return false;
    }
}
