package com.ernesto.charmapp.presentation.activities.patientActivities;

import android.os.Bundle;
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
                        if(!crisisResponse.getError()){
                            if (crisisResponse.getCrisis() == null) {
                                System.out.println("Nueva crisis");
                                getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, HeadacheFragment.create(new Headache(), patient, false), "HEADACHE_FRAGMENT")
                                        .commit();
                            }
                            else{
                                System.out.println("Editar crisis");
                                getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, HeadacheFragment.create(crisisResponse.getCrisis(), patient, true), "HEADACHE_FRAGMENT")
                                        .commit();
                            }
                        }
                        else{
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
