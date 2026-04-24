package com.example.tatwa10;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StageSelectionActivity extends AppCompatActivity {

    private Button btnUrgence, btnPregnant, btnNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_selection);

        btnUrgence = findViewById(R.id.btn_urgence);
        btnPregnant = findViewById(R.id.btn_pregnant);
        btnNormal = findViewById(R.id.btn_normal);

        btnUrgence.setOnClickListener(v -> openReservation("urgence"));
        btnPregnant.setOnClickListener(v -> openReservation("pregnant"));
        btnNormal.setOnClickListener(v -> openReservation("normal"));
    }

    private void openReservation(String stage) {
        Intent intent = new Intent(this, ReservationActivity.class);
        intent.putExtra("stage", stage);
        startActivity(intent);
    }
}