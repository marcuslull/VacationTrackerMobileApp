package edu.wgu.d308pa.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wgu.d308pa.R;
import edu.wgu.d308pa.adapters.RecycleViewAdapter;
import edu.wgu.d308pa.dao.ExcursionDao;
import edu.wgu.d308pa.dao.VacationDao;
import edu.wgu.d308pa.entities.AppDatabase;
import edu.wgu.d308pa.entities.Vacation;

public class VacationFragment extends Fragment {

    Context context;
    AppDatabase appDatabase;
    public static VacationDao vacationDao;
    public static ExcursionDao excursionDao;
    public static Long VacationIdFromLongClick;

    public VacationFragment() {
        super(R.layout.vacation_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        // passing data from activity demo
        int someInt = requireArguments().getInt("some_int"); // getting sample int from the bundle

        // DB setup stuff
        context = getActivity();
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "db").allowMainThreadQueries().build();
        vacationDao = appDatabase.vacationDao();
        excursionDao = appDatabase.excursionDao();

        // clean up the DB
        //vacationDao.deleteAll(vacationDao.getAll());

        // recycler view setup
        RecyclerView recyclerView = view.findViewById(R.id.vacation_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(getDataForVacationRecyclerView(), getParentFragmentManager());
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(layoutManager);

        // for the long click menu
        registerForContextMenu(recyclerView);

        // fab listener
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainerView, AddEditVacationFragment.class, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    // for the long press context menu
    public static boolean isVacationMenu; // bugfix for single ContextMenu but multiple fragments
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        isVacationMenu = true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (isVacationMenu) {
            isVacationMenu = false;
            if (item.getItemId() == R.id.edit_menu_item) {
                AddEditVacationFragment.fromEdit = true;
                getParentFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainerView, AddEditVacationFragment.class, null)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            else if (item.getItemId() == R.id.delete_menu_item) {
                DeleteAlertDialogFragment.isFromExcursion = false; // bugfix from an incomplete delete of excursion
                new DeleteAlertDialogFragment().show(getParentFragmentManager(), null);
                return true;
            }
            return super.onContextItemSelected(item);
        }
        else {
            return super.onContextItemSelected(item);
        }
    }

    // recycler view data population
    public static Map<Long, String> getDataForVacationRecyclerView() {
        List<Vacation> vacations = vacationDao.getAll();
        Map<Long, String> strings = new HashMap<>();
        vacations.forEach(vacation -> {
            strings.put(vacation.getVacationId(), vacation.getTitle());
        });
        return strings;
    }
}
