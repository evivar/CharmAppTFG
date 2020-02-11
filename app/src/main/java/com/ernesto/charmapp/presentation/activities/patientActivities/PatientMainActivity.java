package com.ernesto.charmapp.presentation.activities.patientActivities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.NotificationReceiver;
import com.ernesto.charmapp.data.RetrofitClient;
import com.ernesto.charmapp.data.SharedPreferencesManager;
import com.ernesto.charmapp.domain.Headache;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.interactors.responses.crisisResponses.CrisisResponse;
import com.ernesto.charmapp.presentation.dialogs.LogOutDialog;
import com.ernesto.charmapp.presentation.fragments.patientFragments.HeadacheFragment;
import com.ernesto.charmapp.presentation.fragments.patientFragments.HistoryFragment;
import com.ernesto.charmapp.presentation.fragments.patientFragments.PatientIndexFragment;
import com.ernesto.charmapp.presentation.fragments.patientFragments.PatientProfileFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private FrameLayout fragmentContainer;

    private Patient patient;

    private String patientName;

    private String patientEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main);

        this.patient = SharedPreferencesManager.getInstance(PatientMainActivity.this).getPatient();
        patientName = patient.getName() + " " + patient.getSurname1() + " " + patient.getSurname2();
        patientEmail = patient.getEmail();

        Toolbar toolbar = findViewById(R.id.attackToolbar_attack);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        fragmentContainer = findViewById(R.id.fragmentContainer_patient);
        TextView name = headerView.findViewById(R.id.nameLbl_navHeader);
        TextView email = headerView.findViewById(R.id.emailLbl_navHeader);
        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_copen, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_patient, PatientIndexFragment.create(patient)).commit();
            navigationView.setCheckedItem(R.id.nav_index);
        }
        name.setText(patientName);
        email.setText(patientEmail);
        createDiaryAlarm();
    }

    private void createDiaryAlarm() {
        int flag = 1;
        double freq = 0.01;
        int type = 1; // No estoy seguro de que es esto
        int freqMillis = (int) (freq * 60 * 60 * 1000);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 10); // Poner a la hora, en formato 24h, a la que lanzar la alarma -> 10
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 0);
        // Esto lo pone en el codigo Notificaciones.java que pasaron por el mail
        // c.add(Calendar.DAY_OF_YEAR, 1);

        // Lo mostramos por el log
        Log.d("Alarma de Diario creada", "setalarm freq(hours) = " + freq + " -> alarm @ " +
                c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + " " +
                c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" +
                c.get(Calendar.SECOND));

        // Creamos el alarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Creamos el intent
        Intent intent = new Intent(this, NotificationReceiver.class);
        // Creamos el pending intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Configuramos que se repita la alarma cada freqMillis
        // TODO: Algo falla al poner o el repeating o el c.add(Calendar.DAY_OF_YEAR, freq);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    // TODO: Creo otra vez los metodos de la alarma en el fragment de las crisis, cuando se crea una crisis llamo al createAlarm y cuando se modifica para meter una fecha final llamo al cancelAlarm
    private void cancelAlarm() {
        // Creamos el alarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Creamos el intent
        Intent intent = new Intent(this, NotificationReceiver.class);
        // Creamos el pending intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Cancelamos el alarmManager
        alarmManager.cancel(pendingIntent);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_index:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_patient, PatientIndexFragment.create(patient), "INDEX_FRAGMENT")
                        .commit();
                break;
            case R.id.nav_attack:
                Call<CrisisResponse> readActiveCrisisById = RetrofitClient.getInstance().getAPI().readActiveCrisisById(patient.getPatientId());
                readActiveCrisisById.enqueue(new Callback<CrisisResponse>() {
                    @Override
                    public void onResponse(Call<CrisisResponse> call, Response<CrisisResponse> response) {
                        CrisisResponse crisisResponse = response.body();
                        if (!crisisResponse.getError()) {
                            if (crisisResponse.getCrisis() == null) {
                                Log.i("Crisis:", "Nueva crisis");
                                getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, HeadacheFragment.create(new Headache(), patient, false), "HEADACHE_FRAGMENT")
                                        .commit();
                            } else {
                                Log.i("Crisis:", "Editar crisis");
                                getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, HeadacheFragment.create(crisisResponse.getCrisis(), patient, true), "HEADACHE_FRAGMENT")
                                        .commit();
                            }
                        } else {
                            // Falla la lectura TODO: Revisar esto
                            //Toast.makeText(getSupportFragmentManager().getFragment(), "ERROR EN LA LECTURA DEL HEADACHE", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CrisisResponse> call, Throwable t) {

                    }
                });
                break;
            case R.id.nav_calendar:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_patient, HistoryFragment.create(patient), "HISTORY_FRAGMENT")
                        .commit();
                break;
            case R.id.nav_weather:
                /*getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_attack, WeatherFragment.newInstance("param1", "param2"), "WEATHER_FRAGMENT")
                        .commit();*/

                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_patient, PatientProfileFragment.create(patient), "PROFILE_FRAGMENT")
                        .commit();
                break;
            case R.id.nav_logOut:
                logOut();
                break;
            default:
                Toast.makeText(this, "Error raro", Toast.LENGTH_SHORT).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        LogOutDialog logOutDialog = new LogOutDialog();
        logOutDialog.show(getSupportFragmentManager(), "LOGOUT_DIALOG");
    }
}
