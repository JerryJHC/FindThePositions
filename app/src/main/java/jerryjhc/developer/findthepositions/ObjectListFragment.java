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
        return view;
    }

    public void onChangeAdapter(){
        posicionArrayAdapter.notifyDataSetChanged();
    }

}
