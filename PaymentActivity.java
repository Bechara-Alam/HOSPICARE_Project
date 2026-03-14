package com.example.tatwa10;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tatwa10.ModelClass.Appointment;
import com.google.android.material.card.MaterialCardView;

public class PaymentActivity extends AppCompatActivity {

    private String doctor, date, time, patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        doctor = getIntent().getStringExtra("doctor");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        patientName = getIntent().getStringExtra("patientName");

        // 🔥 Use Material Cards instead of Buttons
        MaterialCardView cardWish = findViewById(R.id.cardWish);
        MaterialCardView cardOMT = findViewById(R.id.cardOMT);


        cardWish.setOnClickListener(v -> showCardDialog("Wish"));
        cardOMT.setOnClickListener(v -> showCardDialog("OMT"));
    }

    private void showCardDialog(String method) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_card_payment, null);

        EditText etCardNumber = view.findViewById(R.id.etCardNumber);
        EditText etCardName = view.findViewById(R.id.etCardName);
        EditText etExpiry = view.findViewById(R.id.etExpiry);
        EditText etCVV = view.findViewById(R.id.etCVV);

        new AlertDialog.Builder(this)
                .setTitle(method + " Payment")
                .setView(view)
                .setPositiveButton("Pay", (dialog, which) -> {

                    String card = etCardNumber.getText().toString();
                    String name = etCardName.getText().toString();
                    String expiry = etExpiry.getText().toString();
                    String cvv = etCVV.getText().toString();

                    if (card.isEmpty() || name.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    saveAppointment(method);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void confirmCash() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Cash Payment")
                .setMessage("Are you sure you will pay cash at hospital?")
                .setPositiveButton("Confirm", (dialog, which) -> saveAppointment("Cash"))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveAppointment(String method) {

        Toast.makeText(this, "Processing Payment...", Toast.LENGTH_SHORT).show();

        new android.os.Handler().postDelayed(() -> {

            Appointment appointment = new Appointment(
                    String.valueOf(System.currentTimeMillis()),
                    doctor,
                    date,
                    time,
                    false,
                    true,
                    patientName,
                    method
            );

            AppointmentStorage.appointmentList.add(appointment);

            new AlertDialog.Builder(this)
                    .setTitle("Payment Successful")
                    .setMessage("Your appointment has been confirmed successfully.")
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .setCancelable(false)
                    .show();

        }, 1500);
    }
}