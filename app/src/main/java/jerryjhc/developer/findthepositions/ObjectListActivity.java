package jerryjhc.developer.findthepositions;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ObjectListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    PosicionAdapter posicionArrayAdapter;
    List<Posicion> posiciones;
    ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_list);

        Intent intent = getIntent();
        posiciones = intent.getParcelableArrayListExtra("posiciones");

        list = (ListView) findViewById(R.id.list_objects);
        posicionArrayAdapter = new PosicionAdapter(this, R.layout.row_list_object, posiciones);
        list.setAdapter(posicionArrayAdapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Posicion posicion = posiciones.get(position);

        if (posicion.getEnc()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(posicion.getNombre());
            builder.setMessage("" + posicion.getSituacion() + "º");
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
