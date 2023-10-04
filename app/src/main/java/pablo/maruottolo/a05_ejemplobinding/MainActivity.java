package pablo.maruottolo.a05_ejemplobinding;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;


import pablo.maruottolo.a05_ejemplobinding.databinding.ActivityMainBinding;
import pablo.maruottolo.a05_ejemplobinding.modelo.Alumno;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addAlumnoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

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
}