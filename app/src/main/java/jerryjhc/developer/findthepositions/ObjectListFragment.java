package jerryjhc.developer.findthepositions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ObjectListFragment extends Fragment {

    View view;
    ListView listView;
    ArrayAdapter<Posicion> posicionArrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.object_list, container, false);

        if (savedInstanceState == null) {
            listView = (ListView) view.findViewById(R.id.list_objects);
            Posicion posicion = new Posicion("Not implemented","0");
            List<Posicion> posiciones = new ArrayList<Posicion>();
            posiciones.add(posicion);
            posicionArrayAdapter = new PosicionAdapter(getActivity(),R.layout.row_list_object, posiciones);
            listView.setAdapter(posicionArrayAdapter);
        }

        return view;
    }

    public void setListObjects(ArrayAdapter<Posicion> posiciones){
        if(posiciones != null ) {
            posicionArrayAdapter = posiciones;
            posicionArrayAdapter.notifyDataSetChanged();

        }else{
            Toast.makeText(getActivity(),"posiciones vacio",Toast.LENGTH_SHORT);
        }
    }

}
