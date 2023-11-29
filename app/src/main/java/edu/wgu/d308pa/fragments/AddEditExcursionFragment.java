package edu.wgu.d308pa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.wgu.d308pa.R;
import edu.wgu.d308pa.dao.ExcursionDao;
import edu.wgu.d308pa.entities.Excursion;

public class AddEditExcursionFragment extends Fragment {

    Button saveButton;
    ExcursionDao excursionDao;
    TextView addEditTitle;
    EditText title, date;
    Bundle receivedBundle;
    Excursion retrievedExcursion;
    public static boolean isEdit = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_edit_excursion_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        saveButton = view.findViewById(R.id.add_or_edit_excursion_save_button);
        excursionDao = VacationFragment.excursionDao;
        addEditTitle = view.findViewById(R.id.add_or_edit_excursion_title_textview);
        title = view.findViewById(R.id.add_or_edit_excursion_title_edittext);
        date = view.findViewById(R.id.add_or_edit_excursion_start_edittext);
        receivedBundle = getArguments();

        if (isEdit) {
            retrievedExcursion = excursionDao.findById(receivedBundle.getLong("excursionId"));
            title.setText(retrievedExcursion.getTitle());
            Long startLong = retrievedExcursion.getDate();
            SimpleDateFormat startFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = new Date(startLong);
            date.setText(startFormat.format(startDate));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Excursion excursion = new Excursion();
                excursion.setTitle(title.getText().toString());
                String dateString = String.valueOf(date.getText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    excursion.setDate(dateFormat.parse(dateString).getTime());
                }
                catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                if (isEdit) {
                    excursion.setExcursionId(retrievedExcursion.getExcursionId());
                    excursion.setVacationId(retrievedExcursion.getVacationId());
                    excursionDao.update(excursion);
                    isEdit = false;
                }
                else {
                    excursion.setVacationId(receivedBundle.getLong("vacationId"));
                    excursionDao.insert(excursion);
                }

                getParentFragmentManager().popBackStack();
            }
        });
    }
}
