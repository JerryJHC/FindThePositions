package jerryjhc.developer.findthepositions;

import android.content.Context;
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
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends FragmentActivity
        implements MainScreenFragment.MainScreenButtonListener,
        SensorEventListener
{

    MainScreenFragment mainScreenFragment;
    ObjectListFragment objectListFragment;
    private List<String> posiciones;
    private List<String> situaciones;

    SensorManager sensorManager;
    Sensor acelerometer, magneticField;
    float[] mGravity,mGeomagnetic;
    float azimut,roll,pitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainScreenFragment = new MainScreenFragment();
        objectListFragment = new ObjectListFragment();

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

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,acelerometer,SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this,magneticField,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    public void transactionFragment() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, objectListFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    @Override
    public void callLoadXML() {
        XmlResourceParser parser = getResources().getXml(R.xml.positions);

        int eventType = -1;
        //String nombre, situacion;

        //try {
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String locationValue = parser.getName();

                    if (locationValue.equals("posicion")) {

                        if(parser.getName().equals("nombre")){
                            posiciones.add(parser.getText());
                        }
                        if(parser.getName().equals("situacion")){
                            situaciones.add(parser.getText());
                        }

                    }
                }
                try {
                    eventType = parser.next();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //objectListFragment.setListObjects(posiciones);


        //} catch (Exception e) {
          //  Toast.makeText(getApplicationContext() , "XML Reader not supported : " + e.toString(), Toast.LENGTH_LONG).show();
        //}

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
                azimut = orientation[0];
                pitch = orientation[1];
                roll = orientation[2];
                Toast.makeText(this,"A: " + azimut + "P: " + pitch + "R: " + roll ,Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
