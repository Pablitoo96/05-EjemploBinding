package pablo.maruottolo.a05_ejemplobinding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import pablo.maruottolo.a05_ejemplobinding.databinding.ActivityAddAlumnoBinding;
import pablo.maruottolo.a05_ejemplobinding.modelo.Alumno;

public class AddAlumnoActivity extends AppCompatActivity {

    private ActivityAddAlumnoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_alumno);

        binding = ActivityAddAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.brnCrearAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recoger la informacion para crear alumno
                Alumno alumno = CrearAlumno();
                if(alumno != null) {
                    //hacer el intent
                    Intent intent = new Intent();

                    //poner el bundle
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ALUMNO",alumno);
                    intent.putExtras(bundle);

                    //comunicar el resultado correcto
                    setResult(RESULT_OK,intent);
                    finish();
                    //terminar

                }else{
                    Toast.makeText(AddAlumnoActivity.this,"FALTAN DATOS",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private Alumno CrearAlumno() {
        if (binding.txtNombre.getText().toString().isEmpty()) {return null;}
        if (binding.txtApellidos.getText().toString().isEmpty()) {return null;}
        if (binding.spCiclosAddAlumno.getSelectedItemPosition() == 0) {return null;}
        if(!binding.rbGrupoAaddAlumno.isChecked()
            && !binding.rbGrupoBaddAlumno.isChecked()
            && !binding.rbGrupoCaddAlumno.isChecked()){return null;}

        RadioButton rb = findViewById(binding.rgGrupoAddAlumno.getCheckedRadioButtonId());
        char grupo = rb.getText().toString().split(" ")[1].charAt(0);
        Alumno alumno = new Alumno(
                binding.txtNombre.getText().toString(),
                binding.txtApellidos.getText().toString(),
                binding.spCiclosAddAlumno.getSelectedItem().toString(),
                grupo
        );
        return alumno;
    }
}