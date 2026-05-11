package com.example.tatwa10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatwa10.ModelClass.Profile;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1001;

    private Button buttonContinueSave;
    private CircleImageView imageViewProfileImage;
    private EditText editTextProfileName;
    private EditText editTextProfileEmail;
    private TextView textViewProfilePhoneNumber;
    private RadioGroup radioGroupProfileSex;
    private EditText editTextProfileAge;

    private Uri imageUri;

    // 🔹 Fake phone number (since no Firebase)
    private String authNumber = "+0000000000";

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        buttonContinueSave = findViewById(R.id.button_profile_continue_save);
        imageViewProfileImage = findViewById(R.id.image_view_profile);
        editTextProfileName = findViewById(R.id.edit_text_profile_name);
        editTextProfileEmail = findViewById(R.id.edit_text_profile_email);
        textViewProfilePhoneNumber = findViewById(R.id.text_view_profile_number);
        radioGroupProfileSex = findViewById(R.id.radio_group_profile_sex);
        editTextProfileAge = findViewById(R.id.edit_text_profile_age);

        textViewProfilePhoneNumber.setText("Phone Number :   " + authNumber);

        buttonContinueSave.setOnClickListener(v -> saveProfile());
        imageViewProfileImage.setOnClickListener(v -> openImagePicker());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void saveProfile() {

        final String name = editTextProfileName.getText().toString().trim();
        final String email = editTextProfileEmail.getText().toString().trim();
        final boolean sex = radioGroupProfileSex.getCheckedRadioButtonId() == R.id.radio_button_profile_male;
        final String age = editTextProfileAge.getText().toString().trim();
        final String phone = authNumber;

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(age)) {
            Toast.makeText(this, "Fields are empty, please fill to proceed", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔹 FRONT-END ONLY: create local profile object (no Firebase)
        profile = new Profile(name, email, phone, sex, age);

        Toast.makeText(this, "Profile Created Successfully (Demo)", Toast.LENGTH_SHORT).show();

        // 🔹 Go to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageViewProfileImage.setImageURI(imageUri);
        }
    }
}
