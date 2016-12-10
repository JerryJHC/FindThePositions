package jerryjhc.developer.findthepositions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class ObjectListFragment extends Fragment {

    String[] msg = {"Not implemented"};
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.object_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.list_objects);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( getActivity() ,android.R.layout.simple_list_item_1, msg);
        listView.setAdapter(adapter);

        return view;
    }

}
