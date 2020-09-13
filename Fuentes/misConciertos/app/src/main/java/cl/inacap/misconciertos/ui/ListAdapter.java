package cl.inacap.misconciertos.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import cl.inacap.misconciertos.R;

import cl.inacap.misconciertos.dto.Evento;

public class ListAdapter extends ArrayAdapter<Evento> {

    private List<Evento> eventosList;
    private Context mcontext;
    private int resourceLayout;


    public ListAdapter(@NonNull Context context, int resource,List<Evento> objects) {
        super(context, resource, objects);
        this.eventosList = objects;
        this.mcontext = context;
        this.resourceLayout = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null)
            view = LayoutInflater.from(mcontext).inflate(resourceLayout,null);

        Evento evento = eventosList.get(position);

        TextView nombre = view.findViewById(R.id.txtViewNombreArtista);
        nombre.setText(evento.getNombreArtista());

        TextView fecha = view.findViewById(R.id.txtViewFecha);
        fecha.setText(evento.getFecha());

        TextView valor = view.findViewById(R.id.txtViewValorEnt);
        valor.setText(""+evento.getEntrada());

        ImageView calificacion = view.findViewById(R.id.imageView);
        calificacion.setImageResource(evento.getCalificacion());


        return view;


}
}
