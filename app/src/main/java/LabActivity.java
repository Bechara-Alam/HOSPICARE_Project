package com.example.tatwa10;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tatwa10.Fragments.LabRequestsFragment;

public class LabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);

        // 🔥 Load Lab Requests Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LabRequestsFragment())
                .commit();
    }
}