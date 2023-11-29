package edu.wgu.d308pa.fragments;

import static edu.wgu.d308pa.fragments.VacationFragment.excursionDao;
import static edu.wgu.d308pa.fragments.VacationFragment.getDataForVacationRecyclerView;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.wgu.d308pa.R;
import edu.wgu.d308pa.adapters.RecycleViewAdapter;
import edu.wgu.d308pa.entities.Excursion;

public class DeleteAlertDialogFragment extends DialogFragment {

    public static boolean fromDetails;
    public static boolean isFromExcursion;
    public static boolean isFromExcursionDetails;
    public static long lastVacationId;
    public static long lastExcursionId;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Long id;
        if (isFromExcursion) {
            id = ExcursionFragment.excursionId;
        } else if (isFromExcursionDetails) {
            id = lastExcursionId;
        } else {
            id = VacationFragment.VacationIdFromLongClick;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm delete id: " + id)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (isFromExcursion || isFromExcursionDetails) {

                        FragmentManager fragmentManager = getParentFragmentManager();
                        if (isFromExcursionDetails){
                            // resolves a viewstack issue coming from the excursion details frag
                            fragmentManager.popBackStack();
                        }

                        isFromExcursion = false;
                        isFromExcursionDetails = false;

                        // delete the excursion and refresh the fragment
                        excursionDao.delete(excursionDao.findById(id));
                        Bundle bundle = new Bundle();
                        bundle.putLong("vacationId", lastVacationId);
                        ExcursionFragment excursionFragment = new ExcursionFragment();
                        excursionFragment.setArguments(bundle);
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.vacation_details_fragment_container_view, excursionFragment);
                        transaction.commit();
                        return;
                    }

                    // check for attached excursions
                    List<Excursion> excursions = excursionDao.getAllWithVacationId(id);
                    if (excursions.size() == 0) {
                        VacationFragment.vacationDao.delete(VacationFragment.vacationDao.findById(id));
                        if (fromDetails) {
                            fromDetails = false;
                            getParentFragmentManager().popBackStack();
                        }
                        else {
                            // refresh the recyclerview
                            RecyclerView recyclerView = getActivity().findViewById(R.id.vacation_recycler_view);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(getDataForVacationRecyclerView(), getParentFragmentManager());
                            recyclerView.setAdapter(recycleViewAdapter);
                            recyclerView.setLayoutManager(layoutManager);
                        }
                    }
                }
            })

            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                }
            });

        return builder.create();
    }
}
