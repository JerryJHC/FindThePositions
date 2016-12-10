package jerryjhc.developer.findthepositions;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends FragmentActivity
        implements MainScreenFragment.MainScreenButtonListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            MainScreenFragment mainScreenFragment = new MainScreenFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mainScreenFragment).commit();

        }

    }

    public void transactionFragment(){
        ObjectListFragment objectListFragment = new ObjectListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, objectListFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }


}
