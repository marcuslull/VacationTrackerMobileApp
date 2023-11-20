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

        // passing data from activity demo
        int someInt = requireArguments().getInt("some_int"); // getting sample int from the bundle

        // DB setup stuff
        context = getActivity();
        assert context != null; // TODO: figure out how to handle null context
        // TODO: figure out how to handle this without the main thread.
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "db").allowMainThreadQueries().build();
        vacationDao = appDatabase.vacationDao();

        // populate some data
        populateSampleDataToDb();

        // recycler view setup
        RecyclerView recyclerView = view.findViewById(R.id.vacation_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(getDataForVacationRecyclerView());
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
                        // sets the fragment to display programmatically
                        .replace(R.id.fragmentContainerView, AddVacationFragment.class, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    // for the long press menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    // for the long press menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        System.out.println(item.getItemId());
        if (item.getItemId() == R.id.edit_menu_item) {
            //TODO: somehow get the vacation ID and edit it
            System.out.println("edit clicked" + item.getItemId());
            return true;
        }
        else if (item.getItemId() == R.id.delete_menu_item) {
            //TODO: somehow get the vacation ID and delete it
            System.out.println("delete clicked" + item.getItemId());
            return true;
        }
        System.out.println("hit the default");
        return super.onContextItemSelected(item);
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

    // for the recycler view
    public Map<Long, String> getDataForVacationRecyclerView() {
        List<Vacation> vacations = vacationDao.getAll();
        Map<Long, String> strings = new HashMap<>();
        vacations.forEach(vacation -> {
            strings.put(vacation.getVacationId(), vacation.getTitle());
        });
        return strings;
    }
}
