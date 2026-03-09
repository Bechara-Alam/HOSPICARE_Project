package com.example.tatwa10.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tatwa10.MainActivity;
import com.example.tatwa10.R;

public class HomeFragment extends Fragment {

    // Patient Service Buttons
    private Button buttonFindDoctors;
    private Button buttonBookAppointment;
    private Button buttonPrescriptions;
    private Button buttonMedicalRecords;
    private Button buttonCallAmbulance;

    // Laboratory Buttons
    private Button buttonBookLab;
    private Button buttonLabResults;
    private Button buttonHomeCollection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        MainActivity.navigationView.setCheckedItem(R.id.nav_home);
        MainActivity.currentFragment = "home";

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize Patient Service Buttons
        buttonFindDoctors = view.findViewById(R.id.button_find_doctors);
        buttonBookAppointment = view.findViewById(R.id.button_book_appointment);
        buttonPrescriptions = view.findViewById(R.id.button_view_prescriptions);
        buttonMedicalRecords = view.findViewById(R.id.button_medical_records);
        buttonCallAmbulance = view.findViewById(R.id.button_emergency);

        // Initialize Laboratory Buttons
        buttonBookLab = view.findViewById(R.id.button_book_lab);
        buttonLabResults = view.findViewById(R.id.button_lab_results);
        buttonHomeCollection = view.findViewById(R.id.button_home_collection);

        // ================= PATIENT SERVICES =================

        buttonFindDoctors.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new FindDoctorsFragment())
                        .addToBackStack(null)
                        .commit()
        );

        buttonBookAppointment.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new BookAppointmentFragment())
                        .addToBackStack(null)
                        .commit()
        );

        buttonPrescriptions.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new PrescriptionFragment())
                        .addToBackStack(null)
                        .commit()
        );



        buttonCallAmbulance.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new CallAmbulanceFragment())
                        .addToBackStack(null)
                        .commit()
        );

        // ================= LABORATORY SERVICES =================

        buttonBookLab.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new BookLabTestFragment())
                        .addToBackStack(null)
                        .commit()
        );

        buttonLabResults.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new LabResultsFragment())
                        .addToBackStack(null)
                        .commit()
        );

        buttonHomeCollection.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeCollectionFragment())
                        .addToBackStack(null)
                        .commit()
        );

        return view;
    }
}