package jerryjhc.developer.findthepositions;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity
        implements MainScreenFragment.MainScreenButtonListener {

    MainScreenFragment mainScreenFragment;
    ObjectListFragment objectListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainScreenFragment = new MainScreenFragment();
        Bundle args = new Bundle();
        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            args.putBoolean("Fragment", true);
            mainScreenFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mainScreenFragment).commit();

        }

    }

    public void transactionFragment() {

        objectListFragment = new ObjectListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, objectListFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

}
