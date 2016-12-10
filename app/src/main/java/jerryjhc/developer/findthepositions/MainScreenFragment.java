package jerryjhc.developer.findthepositions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import jerryjhc.developer.findthepositions.R;

public class MainScreenFragment extends Fragment implements View.OnClickListener {

    Button list_button;
    MainScreenButtonListener mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_screen, container, false);
        list_button = (Button) view.findViewById(R.id.list_button);
        list_button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (MainScreenButtonListener) context;
        } catch (ClassCastException e) {
            String msg = context.toString() + " must implement MainScreenButtonListener";
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        }
    }

    public interface MainScreenButtonListener {
        public void transactionFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.list_button:
                mCallback.transactionFragment();
                break;
        }
    }
}
