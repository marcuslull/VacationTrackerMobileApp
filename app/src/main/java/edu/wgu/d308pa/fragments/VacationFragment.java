package edu.wgu.d308pa.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import edu.wgu.d308pa.R;
import edu.wgu.d308pa.dao.VacationDao;
import edu.wgu.d308pa.entities.AppDatabase;
import edu.wgu.d308pa.entities.Vacation;

public class VacationFragment extends Fragment {
    public VacationFragment() {
        super(R.layout.vacation_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        int someInt = requireArguments().getInt("some_int"); // getting sample int from the bundle

        populateSampleDataToDb();
    }

    public void populateSampleDataToDb() {

        Context context = getActivity();
        assert context != null; // TODO: find out how to deal with null contexts
        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "db").allowMainThreadQueries().build(); // TODO: figure out how to handle this without the main thread.
        VacationDao vacationDao = appDatabase.vacationDao();
        Vacation testVacation = new Vacation();
        testVacation.setTitle("Fiji vacation");
        vacationDao.insert(testVacation);

        Vacation retrievedVacation = vacationDao.findByName("Fiji vacation");
        System.out.println(retrievedVacation.getVacationId());
    }
}
