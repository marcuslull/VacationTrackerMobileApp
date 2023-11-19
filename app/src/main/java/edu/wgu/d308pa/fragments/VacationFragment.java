package edu.wgu.d308pa.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import edu.wgu.d308pa.R;
import edu.wgu.d308pa.adapters.RecycleViewAdapter;
import edu.wgu.d308pa.dao.VacationDao;
import edu.wgu.d308pa.entities.AppDatabase;
import edu.wgu.d308pa.entities.Vacation;

public class VacationFragment extends Fragment {

    Context context;
    AppDatabase appDatabase;
    VacationDao vacationDao;

    public VacationFragment() {
        super(R.layout.vacation_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        int someInt = requireArguments().getInt("some_int"); // getting sample int from the bundle

        context = getActivity();
        assert context != null; // TODO: figure out how to handle null context
        // TODO: figure out how to handle this without the main thread.
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "db").allowMainThreadQueries().build();
        vacationDao = appDatabase.vacationDao();

        populateSampleDataToDb();

        RecyclerView recyclerView = view.findViewById(R.id.vacation_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(getDataForVacationRecyclerView());
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void populateSampleDataToDb() {

        Vacation testVacation1 = new Vacation();
        testVacation1.setTitle("Fiji vacation");
        vacationDao.insert(testVacation1);

        Vacation testVacation2 = new Vacation();
        testVacation2.setTitle("Australia vacation");
        vacationDao.insert(testVacation2);

        Vacation retrievedVacation = vacationDao.findByName("Fiji vacation");
        System.out.println(retrievedVacation.getVacationId());
    }

    public String[] getDataForVacationRecyclerView() {
        List<Vacation> vacations = vacationDao.getAll();
        String[] strings = new String[vacations.size()];
        for (int i=0; i<vacations.size(); i++) {
            strings[i] = vacations.get(i).getTitle();
        }
        return strings;
    }
}
