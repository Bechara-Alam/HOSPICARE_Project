package com.example.tatwa10.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tatwa10.R;

public class HospitalBranchesFragment extends Fragment {

    public HospitalBranchesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hospital_branches, container, false);

        // Beirut
        view.findViewById(R.id.btn_beirut).setOnClickListener(v -> {
            openMap("33.8938,35.5018");
        });

        // Tripoli
        view.findViewById(R.id.btn_tripoli).setOnClickListener(v -> {
            openMap("34.4367,35.8497");
        });

        // Zahle
        view.findViewById(R.id.btn_zahle).setOnClickListener(v -> {
            openMap("33.8462,35.9020");
        });

        return view;
    }

    private void openMap(String latLng) {
        Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + latLng);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);
    }
}