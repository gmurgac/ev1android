package cl.inacap.misconciertos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cl.inacap.misconciertos.dao.EventosDAO;
import cl.inacap.misconciertos.dto.Evento;
import cl.inacap.misconciertos.ui.DatePickerFragment;
import cl.inacap.misconciertos.ui.ListAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView eventosLv;
    ListAdapter eventosAdapter;
    private Spinner spinnerGenero;
    private Spinner spinnerCalificacion;
    private Button registrarBtn;
    private EditText calendarioTxt;
    private EditText nombreArtistaTxt;
    private EditText valorEvento;

    private EventosDAO eventosDAO = new EventosDAO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.calendarioTxt = findViewById(R.id.calendarioTxt);
        this.calendarioTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    switch (view.getId()){
                        case R.id.calendarioTxt:
                            showDatePickerDialog(calendarioTxt);
                            break;
                    }

            }
        });
        this.nombreArtistaTxt = findViewById(R.id.nombreTxt);
        this.valorEvento = findViewById(R.id.valorEntradaTxt);
        this.eventosLv = findViewById(R.id.eventosLv);
        //TODO: PASAR EL CUSTOM ADAPTER, nose como pero está listo xD
        this.eventosAdapter = new ListAdapter(this,R.layout.item_row,eventosDAO.getList());
        this.eventosLv.setAdapter(eventosAdapter);

        this.spinnerCalificacion = findViewById(R.id.spinnerCalificacion);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.calificaciones,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCalificacion.setAdapter(adapter2);
        this.spinnerGenero = findViewById(R.id.idSpinnerGenero);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.generosMusicales,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(adapter);
        this.registrarBtn = findViewById(R.id.registrarBtn);
        this.registrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> errores = new ArrayList<>();
                String nombreArtista = nombreArtistaTxt.getText().toString();
                if(nombreArtista.isEmpty()){
                    errores.add("Nombre vacio");
                }

                String fechaEvento = calendarioTxt.getText().toString();
                if(fechaEvento.isEmpty()){
                    errores.add("Debe seleccionar fecha");
                }
                int entrada=0;
                String valorEntrada = valorEvento.getText().toString().trim();
                if(valorEntrada.isEmpty()){
                    errores.add("Debe ingresar valor de entrada");
                }else{
                    try{
                        entrada = Integer.parseInt(valorEntrada);
                        if(entrada<=0){
                            throw new NumberFormatException();
                        }
                    }catch(Exception ex){
                        errores.add("El valor de entrada debe ser un numero entero mayor que 0");
                    }
                }
                String calificacionTxt = spinnerCalificacion.getSelectedItem().toString();

                int calificacion = Integer.parseInt(calificacionTxt);
                

                String generoTxt = spinnerGenero.getSelectedItem().toString();
                if(errores.isEmpty()){
                    Evento evento = new Evento();
                    evento.setNombreArtista(nombreArtista);
                    evento.setFecha(fechaEvento);
                    evento.setGenero(generoTxt);
                    evento.setEntrada(entrada);
                    evento.setCalificacion(R.mipmap.ic_launcher);
                    eventosDAO.add(evento);
                    eventosAdapter.notifyDataSetChanged();

                }else{
                    mostrarErrores(errores);
                }

            }
        });
    }
    public void mostrarErrores(List<String> errores){
        String mensaje = "";
        for(String e:errores){
            mensaje+= "-" + e + "\n";
        }
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Error de validacion")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar",null)
                .create()
                .show();

    }

    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {


        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

public void showDatePickerDialog(final EditText editText){
    DialogFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            final String selectedDate = day +"/"+(month+1)+"/"+year;
            editText.setText(selectedDate);
        }
    });

    newFragment.show(getSupportFragmentManager(), "datePicker");
}


}