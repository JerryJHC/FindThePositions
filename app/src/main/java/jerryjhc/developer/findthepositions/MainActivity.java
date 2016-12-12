package jerryjhc.developer.findthepositions;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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
        implements MainScreenFragment.MainScreenButtonListener,
        SensorEventListener {

    MainScreenFragment mainScreenFragment;
    ObjectListFragment objectListFragment;
    List<Posicion> posiciones;

    SensorManager sensorManager;
    Sensor acelerometer, magneticField;
    float[] mGravity, mGeomagnetic;
    long degrees;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainScreenFragment = new MainScreenFragment();
        objectListFragment = new ObjectListFragment();

        Bundle args = new Bundle();
        if (findViewById(R.id.fragment_container) != null) {

            args.putBoolean("Fragment", true);
            mainScreenFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mainScreenFragment).commit();

        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void transactionFragment() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, objectListFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    public boolean callLoadXML() {

        InputStream in = getResources().openRawResource(R.raw.positions);
        XMLPullParserPosicion parserPosicion = new XMLPullParserPosicion();
        posiciones = parserPosicion.parse(in, getApplicationContext());
        if (posiciones != null) {
            PosicionAdapter posicionAdapter = new PosicionAdapter(this, R.layout.row_list_object, posiciones);
            objectListFragment.setListObjects(posicionAdapter);
            return true;
        } else {
            Toast.makeText(this, "No hay posiciones para leer", Toast.LENGTH_SHORT);
            return false;
        }


    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
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
                mainScreenFragment.setCoordinates(degrees);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //nothing
    }
}
