package jerryjhc.developer.findthepositions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectListFragment extends Fragment {

    PosicionAdapter posicionArrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.object_list, container, false);
        ListView list = (ListView) view.findViewById(R.id.list_objects);
        InputStream in = getResources().openRawResource(R.raw.positions);
        XMLPullParserPosicion parserPosicion = new XMLPullParserPosicion();
        List<Posicion> posiciones = parserPosicion.parse(in, getActivity());
        PosicionAdapter adapter = new PosicionAdapter(getContext(), R.layout.row_list_object, posiciones);
        list.setAdapter(adapter);
        return view;
    }


}
