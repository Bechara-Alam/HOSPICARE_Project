package com.example.tatwa10;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HospitalAdminActivity extends AppCompatActivity {

    private Button btnReservation, btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_admin);

        btnReservation = findViewById(R.id.btn_reservation);
        btnCreateAccount = findViewById(R.id.btn_create_account);

        // 👉 Reservation page (you can create later)
        btnReservation.setOnClickListener(v ->
                startActivity(new Intent(this, ReservationActivity.class)));

        // 👉 Create account page
        btnCreateAccount.setOnClickListener(v ->
                startActivity(new Intent(this, CreateAccountActivity.class)));
    }
}