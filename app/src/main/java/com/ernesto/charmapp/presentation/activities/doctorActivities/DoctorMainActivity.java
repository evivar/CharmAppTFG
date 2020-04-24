package com.ernesto.charmapp.presentation.activities.doctorActivities;

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
import com.ernesto.charmapp.data.sharedPreferences.SharedPreferencesManager;
import com.ernesto.charmapp.domain.retrofitEntities.Doctor;
import com.ernesto.charmapp.domain.retrofitEntities.RegisterForm;
import com.ernesto.charmapp.presentation.dialogs.LogOutDialog;
import com.ernesto.charmapp.presentation.fragments.doctorFragments.DoctorProfileFragment;
import com.ernesto.charmapp.presentation.fragments.doctorFragments.PatientsFragment;
import com.ernesto.charmapp.presentation.fragments.doctorFragments.PersonalDataFragment;
import com.google.android.material.navigation.NavigationView;

public class DoctorMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private FrameLayout fragmentContainer;

    private Doctor doctor;

    private RegisterForm form;

    private String patientName;

    private String patientEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        this.doctor = SharedPreferencesManager.getInstance(DoctorMainActivity.this).getDoctor();
        patientName = doctor.getName() + " " + doctor.getSurname1() + " " + doctor.getSurname2();
        patientEmail = doctor.getEmail();

        Toolbar toolbar = findViewById(R.id.attackToolbar_attack);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        fragmentContainer = findViewById(R.id.fragmentContainer_doctor);
        TextView name = headerView.findViewById(R.id.nameLbl_navHeader);
        TextView email = headerView.findViewById(R.id.emailLbl_navHeader);
        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_copen, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_doctor, new PatientsFragment()).commit();
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
                // Sera un recycler view con cardsview de cada paciente asignado y cada vez que seleccionas un paciente te abre la informacion de ese paciente
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_patient, new PatientsFragment(), "INDEX_FRAGMENT")
                        .commit();
                break;
            case R.id.nav_register:
                form = new RegisterForm();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_doctor, PersonalDataFragment.create(form), "PERSONAL_DATA_FRAGMENT")
                        .commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_doctor, DoctorProfileFragment.create(doctor), "PROFILE_FRAGMENT")
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
