package cl.inacap.misconciertos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerGenero;
    Spinner spinnerCalificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.spinnerCalificacion = findViewById(R.id.spinnerCalificacion);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.calificaciones,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCalificacion.setAdapter(adapter2);
        this.spinnerGenero = findViewById(R.id.idSpinnerGenero);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.generosMusicales,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(adapter);
    }





    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new SelectorFechaFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public static class SelectorFechaFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),this,year,month,day);

        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            //HACER ALGO CON LA FECHA SELECCIONADA
        }
    }
}