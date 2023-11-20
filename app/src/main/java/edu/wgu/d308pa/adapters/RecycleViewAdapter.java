package edu.wgu.d308pa.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wgu.d308pa.R;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    // info here: https://developer.android.com/develop/ui/views/layout/recyclerview

    private Map<Long, String> localDataMap = new HashMap<>();
    private static Button button;

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

    public RecycleViewAdapter(Map<Long, String> dataSet) {
        localDataMap = dataSet;
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
        holder.getTextView().setText(entry.getValue());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This is the vacation ID
                System.out.println(entry.getKey());
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // This is the vacation ID
                System.out.println(entry.getKey());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataMap.size();
    }
}
