package com.example.tatwa10;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.tatwa10.Fragments.AppointmentFragment;
import com.example.tatwa10.Fragments.EditProfileFragment;
import com.example.tatwa10.Fragments.FindDoctorsFragment;
import com.example.tatwa10.Fragments.HomeFragment;
import com.example.tatwa10.Fragments.HospitalBranchesFragment;
import com.example.tatwa10.Fragments.PrescriptionFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    public static String patientName;
    public static int patientId = 0;
    public static String authNumber;
    private DrawerLayout drawerLayout;
    public static NavigationView navigationView;

    public static String currentFragment = "home";

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    private static final int NOTIFICATION_PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);

        patientId = prefs.getInt("patientId", 0);
        patientName = prefs.getString("name", "");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
        // Request notification permission (Android 13+)
        requestNotificationPermission();

        // Get Firebase token
        getFirebaseToken();

        // Request call permission
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

            } else if (id == R.id.nav_branches) {
                currentFragment = "branches";
                replace(new HospitalBranchesFragment());

            } else if (id == R.id.nav_blog) {
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

    // ==============================
    // GET FIREBASE TOKEN
    // ==============================
    private void getFirebaseToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        Log.e("FCM_TOKEN", "Fetching token failed", task.getException());
                        return;
                    }

                    String token = task.getResult();
                    Log.d("FCM_TOKEN", "Token: " + token);

                    sendTokenToServer(token);
                });
    }

    // ==============================
    // SEND TOKEN TO BACKEND
    // ==============================
    private void sendTokenToServer(String token) {

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        int patientId = prefs.getInt("patientId", 0);

        if (patientId == 0) {
            Log.e("FCM_TOKEN", "Patient ID not found. Token not sent.");
            return;
        }

        new Thread(() -> {
            try {
                ApiService.saveFcmToken(patientId, token);
                Log.d("FCM_TOKEN", "Token sent to server successfully");
            } catch (Exception e) {
                Log.e("FCM_TOKEN", "Error sending token", e);
            }
        }).start();
    }

    // ==============================
    // NOTIFICATION PERMISSION
    // ==============================
    private void requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_CODE
                );
            }
        }
    }

    // ==============================
    // PERMISSION RESULT
    // ==============================
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == NOTIFICATION_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("NOTIFICATION", "Notification permission granted");
            } else {
                Log.d("NOTIFICATION", "Notification permission denied");
            }
        }
    }

    // ==============================
    // REPLACE FRAGMENT
    // ==============================
    private void replace(androidx.fragment.app.Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    // ==============================
    // LOG OUT
    // ==============================
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

    // ==============================
    // BACK BUTTON
    // ==============================
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