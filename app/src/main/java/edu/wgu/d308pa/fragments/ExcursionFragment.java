package edu.wgu.d308pa.fragments;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import edu.wgu.d308pa.R;
import edu.wgu.d308pa.dao.ExcursionDao;
import edu.wgu.d308pa.dao.VacationDao;
import edu.wgu.d308pa.entities.Excursion;

public class ExcursionFragment extends Fragment {
    VacationDao vacationDao;
    ExcursionDao excursionDao;
    Button addButton;
    FragmentManager fragmentManager;
    Fragment addEditExcursionFragment;
    Fragment detailsExcursionFragment;
    Bundle receivedBundle;
    public static long excursionId;
    public static long lastVacationId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.excursion_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        vacationDao = VacationFragment.vacationDao;
        excursionDao = VacationFragment.excursionDao;
        addButton = view.findViewById(R.id.excursion_fragment_add_button);
        receivedBundle = getArguments();

        // display a button for each associated excursion
        List<Excursion> excursions = excursionDao.getAllWithVacationId(receivedBundle.getLong("vacationId"));
        for (Excursion excursion : excursions) {
            Button button = new Button(getContext());
            button.setText(excursion.getExcursionId() + " " + excursion.getTitle());
            registerForContextMenu(button); // for the long press menu
            ViewGroup fragmentLayout = getView().findViewById(R.id.excursion_fragment_layout_linearlayout);
            fragmentLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    String[] splitTitle = String.valueOf(button.getText()).split(" ");
                    Bundle bundle = new Bundle();
                    bundle.putLong("excursionId", Long.parseLong(splitTitle[0]));
                    bundle.putLong("vacationId", receivedBundle.getLong("vacationId"));
                    fragmentManager = getParentFragmentManager();
                    detailsExcursionFragment = new ExcursionDetailsFragment();
                    detailsExcursionFragment.setArguments(bundle);
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.vacation_details_fragment_container_view, detailsExcursionFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = receivedBundle;
                fragmentManager = getParentFragmentManager();
                addEditExcursionFragment = new AddEditExcursionFragment();
                addEditExcursionFragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.vacation_details_fragment_container_view, addEditExcursionFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    public static boolean isExcursionMenu; // bugfix for single ContextMenu but multiple fragments
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Button button = (Button) v;
        String[] splitTitle = String.valueOf(button.getText()).split(" ");
        excursionId = Long.parseLong(splitTitle[0]);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        isExcursionMenu = true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (isExcursionMenu) {
            isExcursionMenu = false;
            if (item.getItemId() == R.id.edit_menu_item) {
                Bundle bundle = new Bundle();
                bundle.putLong("excursionId", excursionId);
                bundle.putLong("vacationId", receivedBundle.getLong("vacationId"));
                Fragment addEditFragment = new AddEditExcursionFragment();
                addEditFragment.setArguments(bundle);
                getParentFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.vacation_details_fragment_container_view, addEditFragment, null)
                        .addToBackStack(null)
                        .commit();
                AddEditExcursionFragment.isEdit = true;
                return true;
            }
            else if (item.getItemId() == R.id.delete_menu_item) {
                DeleteAlertDialogFragment.isFromExcursion = true;
                new DeleteAlertDialogFragment().show(getParentFragmentManager(), null);
                return true;
            }
            return super.onContextItemSelected(item);
        }
        else {
            return super.onContextItemSelected(item);
        }
    }
}