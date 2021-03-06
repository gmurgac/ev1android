package cl.inacap.misconciertos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.List;

import cl.inacap.misconciertos.dao.EventosDAO;
import cl.inacap.misconciertos.dto.Evento;
import cl.inacap.misconciertos.ui.DatePickerFragment;
import cl.inacap.misconciertos.ui.ListAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView eventosLv;
    private ListAdapter eventosAdapter;
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

        this.eventosAdapter = new ListAdapter(this,R.layout.item_row,eventosDAO.getList());
        this.eventosLv.setAdapter(eventosAdapter);
        String[] listaCal = new String[]{"-","1","2","3","4","5","6","7"};
        this.spinnerCalificacion = findViewById(R.id.spinnerCalificacion);
        ArrayAdapter<String> adapterSCalif = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listaCal);
        adapterSCalif.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCalificacion.setAdapter(adapterSCalif);
        String[] listaGen = new String[]{"-","Rock","Jazz","Pop","Regueton","Salsa","Metal"};
        this.spinnerGenero = findViewById(R.id.idSpinnerGenero);
        ArrayAdapter<String> adapterSGeneros = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listaGen);
        adapterSGeneros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(adapterSGeneros);
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
                int calificacion = 0;
                try{
                    calificacion = Integer.parseInt(calificacionTxt);
                }catch (Exception ex){
                    errores.add("Debe calificar al evento con nota de 1 a 7");
                }


                String generoTxt = spinnerGenero.getSelectedItem().toString();
                if(generoTxt.equalsIgnoreCase("-")){
                    errores.add("Debe seleccionar el genero musical");
                }



                if(errores.isEmpty()){
                    Evento evento = new Evento();
                    evento.setNombreArtista(nombreArtista);
                    evento.setFecha(fechaEvento);
                    evento.setGenero(generoTxt);
                    evento.setEntrada(entrada);
                    //guardar icono como calificacion
                    if(calificacion<=3){
                        calificacion = R.drawable.ic_aburrido;
                    }else if(calificacion <=5){
                        calificacion = R.drawable.ic_pulgares;
                    }else{
                        calificacion = R.drawable.ic_mano;
                    }
                    evento.setCalificacion(calificacion);
                    eventosDAO.add(evento);
                    eventosAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,"Exito registro evento",Toast.LENGTH_SHORT).show();
                    //reiniciar campos
                    nombreArtistaTxt.setText("");
                    calendarioTxt.setText("");
                    spinnerGenero.setSelection(0);
                    spinnerCalificacion.setSelection(0);
                    valorEvento.setText("");

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

        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

public void showDatePickerDialog(final EditText editText){
    DialogFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            String dia;
            String mes;
            if(day<10){
                dia = "0"+String.valueOf(day);
            }else{
                dia=String.valueOf(day);
            }
            if(month+1<10){
                mes = "0"+String.valueOf(month+1);
            }else{
                mes = String.valueOf(month+1);
            }



            final String selectedDate = dia +"/"+mes+"/"+year;
            editText.setText(selectedDate);
        }
    });

    newFragment.show(getSupportFragmentManager(), "datePicker");
}


}