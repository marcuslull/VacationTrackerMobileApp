package edu.wgu.d308pa;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.wgu.d308pa.fragments.VacationFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            // transfer data to the fragment example
            Bundle bundle = new Bundle();
            bundle.putInt("some_int", 0);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    // sets the fragment to display programmatically
                    .add(R.id.fragmentContainerView, VacationFragment.class, bundle) // bundle can be null if there is no data to transfer
                    .commit();
        }
    }
}