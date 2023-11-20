package edu.wgu.d308pa.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wgu.d308pa.R;
import edu.wgu.d308pa.fragments.AddEditVacationFragment;
import edu.wgu.d308pa.fragments.VacationDetailsFragment;
import edu.wgu.d308pa.fragments.VacationFragment;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    // info here: https://developer.android.com/develop/ui/views/layout/recyclerview

    private Map<Long, String> localDataMap = new HashMap<>();

    // TODO: Resolve this static field
    private static Button button;

    private FragmentManager fragmentManager;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.recycleView_button);
            RecycleViewAdapter.button = (Button) view.findViewById(R.id.recycleView_button);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public RecycleViewAdapter(Map<Long, String> dataSet, FragmentManager fragManager) {
        localDataMap = dataSet;
        fragmentManager = fragManager;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_text_rowitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {

        List<Map.Entry<Long, String>> entryList = new ArrayList<>(localDataMap.entrySet());
        Map.Entry<Long, String> entry = entryList.get(position);
        String buttonText = entry.getKey() + ": " + entry.getValue();
        holder.getTextView().setText(buttonText);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VacationFragment.VacationIdFromLongClick = entry.getKey();
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainerView, VacationDetailsFragment.class, null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                VacationFragment.VacationIdFromLongClick = entry.getKey();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataMap.size();
    }
}
