package edu.wgu.d308pa.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.wgu.d308pa.R;

public class VacationFragment extends Fragment {
    public VacationFragment() {
        super(R.layout.vacation_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        int someInt = requireArguments().getInt("some_int"); // getting sample int from the bundle
    }
}
