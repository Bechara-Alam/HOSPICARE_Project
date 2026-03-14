package com.example.tatwa10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneVerificationActivity extends AppCompatActivity {

    private EditText editTextPhoneNumber;
    private EditText editTextOtp;
    private Button buttonGenerateOtp;
    private Button buttonVerifyNumber;

    // ⭐ Fake OTP for frontend testing
    private static final String DEMO_OTP = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        editTextPhoneNumber = findViewById(R.id.edit_text_verify_phone_number);
        editTextOtp = findViewById(R.id.edit_text_verify_otp);
        buttonGenerateOtp = findViewById(R.id.button_generate_otp);
        buttonVerifyNumber = findViewById(R.id.button_verify_number);

        // Generate OTP (fake)
        buttonGenerateOtp.setOnClickListener(v -> {
            String phone = editTextPhoneNumber.getText().toString().trim();

            if (TextUtils.isEmpty(phone) || phone.length() < 8) {
                Toast.makeText(this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Demo OTP is 123456", Toast.LENGTH_LONG).show();
        });

        // Verify OTP locally
        buttonVerifyNumber.setOnClickListener(v -> {
            String phone = editTextPhoneNumber.getText().toString().trim();
            String otp = editTextOtp.getText().toString().trim();

            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!DEMO_OTP.equals(otp)) {
                Toast.makeText(this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                return;
            }

            // ⭐ Fake first-time / returning user logic
            if (phone.endsWith("0")) {
                // New user → create profile
                Toast.makeText(this, "Create your profile to continue", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AddProfileActivity.class));
            } else {
                // Existing user → go to main
                Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            finish();
        });
    }
}