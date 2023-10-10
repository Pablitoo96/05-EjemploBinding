package pablo.maruottolo.a05_ejemplobinding;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import pablo.maruottolo.a05_ejemplobinding.databinding.ActivityMainBinding;
import pablo.maruottolo.a05_ejemplobinding.modelo.Alumno;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addAlumnoLauncher;
    private ArrayList<Alumno> listaAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        listaAlumno = new ArrayList<>();

        inicializarLaucher();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir la Activity addAlumno
                addAlumnoLauncher.launch(new Intent(MainActivity.this, AddAlumnoActivity.class));


            }
        });
    }

    private void inicializarLaucher() {
        addAlumnoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            if(result.getData() != null && result.getData().getExtras()!=null){
                                Alumno alumno = (Alumno)result.getData().getExtras().getSerializable("ALUMNO");
                                listaAlumno.add(alumno);
                                mostarAlumnos();
                                //FALTA MOSTRAR INFORMACION!!
                                //1.Un elemento para mostrar la informacion --->TextView
                                //2.Un contenedor de datos a mostrar ---> Lista alumnos
                                //3.Un contenedor donde mostrar caca uno de los elementos --->Scroll
                                //4.La logica para mostrar los elementos
                            }else{
                                Toast.makeText(MainActivity.this,"NO HAY INFORMACIÓN",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"ACCIÓN CANCELADA",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void mostarAlumnos() {
        binding.contentMain.contenedorMain.removeAllViews();
        for(Alumno a : listaAlumno){
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

            View alumnoView = layoutInflater.inflate(R.layout.alumno_fila_view,null);
            TextView lbNombre = alumnoView.findViewById(R.id.lbNombreAlumnoView);
            TextView lbApellidos = alumnoView.findViewById(R.id.lbApellidosAlumnosView);
            TextView lbCiclos = alumnoView.findViewById(R.id.lbCicloAlumnoView);
            TextView lbGrupo = alumnoView.findViewById(R.id.lbGrupoAlumnoView);

            lbNombre.setText(a.getNombre());
            lbApellidos.setText(a.getApellidos());
            lbCiclos.setText(a.getCiclo());
            lbGrupo.setText(String.valueOf(a.getGrupo()));

            binding.contentMain.contenedorMain.addView(alumnoView);
        }
    }
}