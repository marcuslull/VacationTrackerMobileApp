package edu.wgu.d308pa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import edu.wgu.d308pa.R;
import edu.wgu.d308pa.dao.ExcursionDao;
import edu.wgu.d308pa.entities.Excursion;

public class AddEditExcursionFragment extends Fragment {

    Button saveButton;
    ExcursionDao excursionDao;
    EditText title, date;
    Bundle receivedBundle;

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
        title = view.findViewById(R.id.add_or_edit_excursion_title_edittext);
        date = view.findViewById(R.id.add_or_edit_excursion_start_edittext);
        receivedBundle = getArguments();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Excursion excursion = new Excursion();
                excursion.setVacationId(receivedBundle.getLong("vacationId"));
                excursion.setTitle(title.getText().toString());
                String dateString = String.valueOf(date.getText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    excursion.setDate(dateFormat.parse(dateString).getTime());
                }
                catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                excursionDao.insert(excursion);
                getParentFragmentManager().popBackStack();
            }
        });
    }
}
