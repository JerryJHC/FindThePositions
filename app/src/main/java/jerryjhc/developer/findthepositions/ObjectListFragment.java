package jerryjhc.developer.findthepositions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectListFragment extends Fragment implements AdapterView.OnItemClickListener {

    PosicionAdapter posicionArrayAdapter;
    List<Posicion> posiciones;
    ListView list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.object_list, container, false);
        posiciones = getArguments().getParcelableArrayList("posiciones");
        list = (ListView) view.findViewById(R.id.list_objects);
        posicionArrayAdapter = new PosicionAdapter(getContext(), R.layout.row_list_object, posiciones);
        list.setAdapter(posicionArrayAdapter);
        list.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Posicion posicion = posiciones.get(position);
        if (posicion.getEnc()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(posicion.getNombre());
            builder.setMessage("" + posicion.getSituacion() + "º");
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

}
