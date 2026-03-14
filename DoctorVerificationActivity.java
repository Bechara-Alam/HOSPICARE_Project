package com.example.tatwa10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class DoctorVerificationActivity extends AppCompatActivity {

    private EditText editTextPassword;
    private Button buttonLogin;
    private Spinner spinnerDoctor;
    private ProgressDialog dialog;

    // 🔹 Fake local doctor passwords (FRONT-END DEMO)
    private Map<String, String> doctorPasswords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_verification);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Signing In... Please wait");

        editTextPassword = findViewById(R.id.edit_text_doctor_password);
        buttonLogin = findViewById(R.id.button_doctor_login);
        spinnerDoctor = findViewById(R.id.spinner_doctors_login);

        // 🔹 Spinner doctor names from resources
        String[] names = getResources().getStringArray(R.array.doctors_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                names
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapter);

        // 🔹 Demo passwords (same for simplicity)
        doctorPasswords = new HashMap<>();
        for (String doctorName : names) {
            doctorPasswords.put(doctorName, "12345678"); // demo password
        }

        buttonLogin.setOnClickListener(v -> loginDoctor());
    }

    private void loginDoctor() {

        String name = spinnerDoctor.getSelectedItem().toString();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(password) || password.length() < 8) {
            Toast.makeText(this, "Please enter a valid password (min 8 chars)", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.show();

        // 🔹 FRONT-END PASSWORD CHECK (NO FIREBASE)
        String correctPassword = doctorPasswords.get(name);

        if (correctPassword != null && correctPassword.equals(password)) {

            dialog.dismiss();
            Toast.makeText(this, "Login Successful (Demo)", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, DoctorMainActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);

        } else {
            dialog.dismiss();
            Toast.makeText(this, "Incorrect Password, Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
