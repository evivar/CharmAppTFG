package com.ernesto.charmapp.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.presentation.activities.doctorActivities.DoctorLoginActivity;
import com.ernesto.charmapp.presentation.activities.patientActivities.PatientLoginActivity;

public class MainActivity extends AppCompatActivity {

    private Button patientBtn;

    private Button doctorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.patientBtn = findViewById(R.id.patientBtn_main);
        this.patientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PatientLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        this.doctorBtn = findViewById(R.id.doctorBtn_main);
        this.doctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DoctorLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }


}

/*
TODO:
    - Quitar todos los System.out.println y cambiarlos por Log CREO OK
    - Temperatura, estado del cielo y precipitaciones cogiendo la API de OpenWeatherMap: https://openweathermap.org/price
        - Temperatura: main -> temp
        - Estado del cielo: whather -> main
        - Precipitaciones(%): No hay
    - En el formulario de crisis:
        - Estaria guapo poder modificar el encabezado dependiendo de si está editando la crisis o es una nueva OK
        - ¿Esto que poyas es? Cambiralo
        - Si selecciona una fecha de inicio o una fecha de fin posteriores al dia actual mostrar un dialogo de error OK
        - Si finaliza una crisis quitar la alarma; si la crea SIN FECHA DE FIN crear la alarma CREO OK
    - En el menu lateral quitar la sombra de la opcion seleccionada OK
    - En el perfil quitar el focus del cambiar contraseña OK
    - En todos los fragment, excepto que sea a la hora de elegir una fecha, o en algun tipo de formulario largo, el boton de atras tiene que llevar al inicio
    - En el diario, rellenar bien todos los campos (Falta el del deporte)
    - Revisar los dialogos de error para ponerlos bonitos y ver que todos los errores aparecen
        - Crisis OK
        - Diario OK
        - Perfil(Por si acaso) OK
    - En el fragment de HistoryCrisisAndDiary:
        - Poner la fecha bien dd/mm/aaaa
        - Cuando guardas algun cambio contraer el layout
    - La alarma suena, falta comprobar la vibracion
 */
