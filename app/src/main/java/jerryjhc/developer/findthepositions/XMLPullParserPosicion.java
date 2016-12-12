package jerryjhc.developer.findthepositions;

import android.content.Context;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserPosicion {

    private String KEY_POSITION = "posicion";
    private String KEY_NAME = "nombre";
    private String KEY_SITUACION = "situacion";

    List<Posicion> posiciones;
    Posicion posicion;
    String text;

    public XMLPullParserPosicion() {
        posiciones = new ArrayList<Posicion>();
    }

    public List<Posicion> getEmployees() {
        return posiciones;
    }

    public List<Posicion> parse(InputStream is, Context context) {

        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        if (tagname.equalsIgnoreCase("employee")) {
                            posicion = new Posicion();
                        }
                        break;

                    case XmlPullParser.TEXT:

                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if (tagname.equalsIgnoreCase(KEY_POSITION)) {
                            posiciones.add(posicion);
                        } else if (tagname.equalsIgnoreCase(KEY_NAME)) {
                            posicion.setNombre(text);
                        } else if (tagname.equalsIgnoreCase(KEY_SITUACION)) {
                            posicion.setSituacion(text);
                        }

                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            Toast.makeText(context,"Error leyendo XML",Toast.LENGTH_SHORT);
        }
        return posiciones;
    }
}
