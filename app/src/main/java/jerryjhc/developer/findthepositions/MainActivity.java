package jerryjhc.developer.findthepositions;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity
        implements
        SensorEventListener, View.OnClickListener {

    ObjectListFragment objectListFragment;
    List<Posicion> posiciones;

    Button list_objects, loadXML;
    TextView instruccion, degreesTV;

    Boolean act_LoadXML = true;
    Boolean act_frag = false;

    String titleEnc, messageEnc;

    SensorManager sensorManager;
    Sensor acelerometer, magneticField;
    float[] mGravity, mGeomagnetic;
    long degrees;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadXML = (Button) findViewById(R.id.loadXML_button);
        instruccion = (TextView) findViewById(R.id.instruccion);

        if (savedInstanceState != null)
            if (savedInstanceState.containsKey("act_LoadXML"))
                act_LoadXML = savedInstanceState.getBoolean("act_LoadXML");

        if (act_LoadXML)
            loadXML.setOnClickListener(this);
        else {
            loadXML.setEnabled(act_LoadXML);
            posiciones = savedInstanceState.getParcelableArrayList("posiciones");
        }

        instruccion.setText(String.format(getString(R.string.instruccion), (act_LoadXML) ? "?" : posiciones.size()));

        if (findViewById(R.id.largeframe) != null && !act_LoadXML)
            transactionFragment();

        degreesTV = (TextView) findViewById(R.id.degrees);

        Bundle args = new Bundle();
        if (findViewById(R.id.list_button) != null) {

            if (savedInstanceState != null) {
                return;
            }

            list_objects = (Button) findViewById(R.id.list_button);

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        titleEnc = getString(R.string.titleEncontrado);
        messageEnc = getString(R.string.messageEncontrado);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("act_LoadXML", act_LoadXML);
        if (posiciones != null)
            outState.putParcelableArrayList("posiciones", (ArrayList<? extends Parcelable>) posiciones);
        super.onSaveInstanceState(outState);
    }

    public void transactionFragment() {
        if (findViewById(R.id.largeframe) != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle args = new Bundle();
            objectListFragment = new ObjectListFragment();
            args.putParcelableArrayList("posiciones", (ArrayList<? extends Parcelable>) posiciones);
            objectListFragment.setArguments(args);
            if (!act_frag) {
                transaction.add(R.id.largeframe, objectListFragment).commit();
                act_frag = true;
            } else
                transaction.replace(R.id.largeframe, objectListFragment).commit();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                mGravity = sensorEvent.values;
            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                mGeomagnetic = sensorEvent.values;
            if (mGravity != null && mGeomagnetic != null) {
                float R[] = new float[9];
                float I[] = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
                if (success) {
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    degrees = Math.round(((Math.toDegrees(orientation[0]) + 360) % 360));
                    degreesTV.setText("" + degrees + " º");

                    if (posiciones != null) {

                        for (Posicion posicion : posiciones) {
                            if (degrees <= (posicion.getSituacion() + 10) && degrees >= (posicion.getSituacion() - 10)) {
                                if (!posicion.getEnc()) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                    builder.setTitle(titleEnc);
                                    builder.setMessage(messageEnc + posicion.getNombre() + "!");
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                    posicion.setEnc(true);
                                    transactionFragment();
                                    break;
                                }
                            }
                        }

                    }

                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //nothing
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loadXML_button:
                InputStream in = getResources().openRawResource(R.raw.positions);
                XMLPullParserPosicion parserPosicion = new XMLPullParserPosicion();
                posiciones = parserPosicion.parse(in, getApplicationContext());
                instruccion.setText(String.format(getString(R.string.instruccion), "" + posiciones.size()));
                loadXML.setEnabled(false);
                act_LoadXML = false;
                transactionFragment();
                break;
        }
    }

}
