package com.example.tatwa10;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tatwa10.Fragments.AppointmentFragment;
import com.example.tatwa10.Fragments.EditProfileFragment;
import com.example.tatwa10.Fragments.FindDoctorsFragment;
import com.example.tatwa10.Fragments.HomeFragment;
import com.example.tatwa10.Fragments.HospitalBranchesFragment; // ⭐ NEW

import com.example.tatwa10.Fragments.PrescriptionFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    public static NavigationView navigationView;

    public static String currentFragment = "home";
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    public static String patientName = "John Doe";
    public static String authNumber = "+961000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CALL_PHONE},
                1
        );

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                currentFragment = "home";
                replace(new HomeFragment());

            } else if (id == R.id.nav_doctors) {
                currentFragment = "doctors";
                replace(new FindDoctorsFragment());

            } else if (id == R.id.nav_appointment) {
                currentFragment = "appointment";
                replace(new AppointmentFragment());

            } else if (id == R.id.nav_prescription) {
                currentFragment = "prescription";
                replace(new PrescriptionFragment());

            } else if (id == R.id.nav_edit_profile) {
                currentFragment = "edit_profile";
                replace(new EditProfileFragment());

            }

            else if (id == R.id.nav_branches) {
                currentFragment = "branches";
                replace(new HospitalBranchesFragment());
            }

            else if (id == R.id.nav_blog) {

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://keen-cobbler-068145.netlify.app/#about")
                );
                startActivity(intent);

            } else if (id == R.id.nav_contact_us) {

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://keen-cobbler-068145.netlify.app/#contact")
                );
                startActivity(intent);
            } else if (id == R.id.nav_log_out) {
                logOut();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this,
                        drawerLayout,
                        toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close
                );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            replace(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private void replace(androidx.fragment.app.Fragment f) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, f)
                .commit();
    }

    private void logOut() {
        new AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes", (d, w) -> {
                    startActivity(new Intent(this, StartingActivity.class));
                    finish();
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

        if ("home".equals(currentFragment)) {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            } else {
                Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();
                mBackPressed = System.currentTimeMillis();
            }
        } else {
            super.onBackPressed();
        }
    }
}