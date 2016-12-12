package jerryjhc.developer.findthepositions;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Formatter;

import jerryjhc.developer.findthepositions.R;

public class MainScreenFragment extends Fragment implements View.OnClickListener {

    Button list_button, loadXML_button, start_button;
    TextView instruccion, degreesTV;
    MainScreenButtonListener mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_screen, container, false);
        Bundle args = this.getArguments();
        list_button = (Button) view.findViewById(R.id.list_button);

        loadXML_button = (Button) view.findViewById(R.id.loadXML_button);
        loadXML_button.setOnClickListener(this);

        start_button = (Button) view.findViewById(R.id.start_button);
        start_button.setEnabled(false);

        instruccion = (TextView) view.findViewById(R.id.instruccion);
        instruccion.setText(String.format(getString(R.string.instruccion), new String("?")));

        degreesTV = (TextView) view.findViewById(R.id.degrees);

        if (args != null)
            list_button.setOnClickListener(this);
        else
            list_button.setVisibility(View.GONE);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (MainScreenButtonListener) context;
        } catch (ClassCastException e) {
            String msg = context.toString() + " must implement MainScreenButtonListener";
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public interface MainScreenButtonListener {
        public void transactionFragment();
        public boolean callLoadXML();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.list_button:
                mCallback.transactionFragment();
                break;

            case R.id.loadXML_button:
                if (mCallback.callLoadXML())
                    loadXML_button.setEnabled(false);
                break;
        }
    }

    public void setCoordinates(long degrees) {
        degreesTV.setText("Degrees: " + degrees);
    }
}
