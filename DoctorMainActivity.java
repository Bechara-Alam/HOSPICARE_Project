package com.example.tatwa10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tatwa10.FragmentDoctors.ApproveAppointmentFragment;
import com.example.tatwa10.FragmentDoctors.CompletedAppointmentFragment;
import com.example.tatwa10.FragmentDoctors.HomeDoctorsFragment;
import com.example.tatwa10.FragmentDoctors.PatientPrescriptionFragment;
import com.example.tatwa10.FragmentDoctors.PendingAppointmentFragment;
import com.google.android.material.navigation.NavigationView;

public class DoctorMainActivity extends AppCompatActivity {

    private static final long TIME_INTERVAL = 2000;

    private DrawerLayout drawerLayout;
    private long mBackPressed;
    public static NavigationView navigationView;


    public static final String SHARED_PREFERENCES = "shared_prefs";
    public static String doctorName;
    public static String currentFragment = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);

        drawerLayout = findViewById(R.id.drawer_layout_doctor);
        Toolbar toolbar = findViewById(R.id.toolbar_doctor);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigation_view_doctor);

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home2) {
                currentFragment = "home";
                replace(new HomeDoctorsFragment());

            } else if (id == R.id.nav_approve_appointment2) {
                currentFragment = "approve";
                replace(new ApproveAppointmentFragment());

            } else if (id == R.id.nav_pending_appointment2) {
                currentFragment = "pending";
                replace(new PendingAppointmentFragment());

            } else if (id == R.id.nav_completed_appointment2) {
                currentFragment = "completed";
                replace(new CompletedAppointmentFragment());

            } else if (id == R.id.nav_prescription2) {
                currentFragment = "prescription";
                replace(new PatientPrescriptionFragment());

            } else if (id == R.id.nav_log_out2) {
                logOut();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            replace(new HomeDoctorsFragment());
            navigationView.setCheckedItem(R.id.nav_home2);
        }

        SharedPreferences sp = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        doctorName = sp.getString("name", "Doctor");
    }

    private void replace(androidx.fragment.app.Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_doctor, fragment)
                .commit();
    }

    private void logOut() {
        new AlertDialog.Builder(this)
                .setTitle("Log Out?")
                .setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes", (d, w) -> {
                    SharedPreferences sp = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
                    sp.edit().remove("name").apply();
                    startActivity(new Intent(this, StartingActivity.class));
                })
                .setNegativeButton("No", (d, w) -> d.cancel())
                .show();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            currentFragment = "home";
            navigationView.setCheckedItem(R.id.nav_home2);
            return;
        }

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
        }
    }
}
