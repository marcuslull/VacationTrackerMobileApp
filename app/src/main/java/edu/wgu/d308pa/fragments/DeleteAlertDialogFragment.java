package edu.wgu.d308pa.fragments;
import static edu.wgu.d308pa.fragments.VacationFragment.getDataForVacationRecyclerView;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.wgu.d308pa.R;
import edu.wgu.d308pa.adapters.RecycleViewAdapter;
import edu.wgu.d308pa.entities.Excursion;

public class DeleteAlertDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Long vacationId = VacationFragment.VacationIdFromLongClick;
        builder.setMessage("Are you sure you want to delete: " + vacationId)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // check for attached excursions
                        List<Excursion> excursions = VacationFragment.excursionDao.getAllWithVacationId(vacationId);
                        if (excursions.size() == 0) {
                            VacationFragment.vacationDao.delete(VacationFragment.vacationDao.findById(vacationId));

                            // refresh the recyclerview
                            RecyclerView recyclerView = getActivity().findViewById(R.id.vacation_recycler_view);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(getDataForVacationRecyclerView(), getParentFragmentManager());
                            recyclerView.setAdapter(recycleViewAdapter);
                            recyclerView.setLayoutManager(layoutManager);
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
