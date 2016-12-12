package jerryjhc.developer.findthepositions;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PosicionAdapter extends ArrayAdapter<Posicion> {
    Context context;
    int LayoutResourceId;
    List<Posicion> data = null;

    public PosicionAdapter(Context context, int LayoutResourceId, List<Posicion> data) {
        super(context, LayoutResourceId, data);

        this.context = context;
        this.LayoutResourceId = LayoutResourceId;
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        PosicionHolder posicionHolder = null;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(LayoutResourceId, parent, false);

            posicionHolder = new PosicionHolder();

            posicionHolder.nom = (TextView) view.findViewById(R.id.nombreObject);
            posicionHolder.enc = (TextView) view.findViewById(R.id.encObject);

            view.setTag(posicionHolder);

        } else {
            posicionHolder = (PosicionHolder) view.getTag();
        }

        Posicion posicion = data.get(position);
        posicionHolder.nom.setText(posicion.getNombre());
        posicionHolder.enc.setText(""+posicion.getEnc());

        return view;
    }

    static class PosicionHolder {
        TextView nom;
        TextView enc;
    }
}
