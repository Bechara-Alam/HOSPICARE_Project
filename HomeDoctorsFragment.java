package com.example.tatwa10.FragmentDoctors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tatwa10.DoctorMainActivity;
import com.example.tatwa10.R;

public class HomeDoctorsFragment extends Fragment {

    private TextView textViewName;

    // Appointment Buttons
    private Button buttonApproveAppointment;
    private Button buttonPendingAppointment;
    private Button buttonCompletedAppointment;
    private Button buttonPatientPrescription;

    // Laboratory Buttons
    private Button buttonViewLabResults;
    private Button buttonRequestLabTest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_doctors, container, false);

        // Set Navigation Selection
        DoctorMainActivity.navigationView.setCheckedItem(R.id.nav_home2);
        DoctorMainActivity.currentFragment = "home";

        // Doctor Name
        textViewName = view.findViewById(R.id.text_view_home_name);
        textViewName.setText(DoctorMainActivity.doctorName);

        // Initialize Appointment Buttons
        buttonApproveAppointment = view.findViewById(R.id.button_approve_appointments);
        buttonPendingAppointment = view.findViewById(R.id.button_pending_appointments);
        buttonCompletedAppointment = view.findViewById(R.id.button_completed_appointments);
        buttonPatientPrescription = view.findViewById(R.id.button_your_patients);

        // Initialize Laboratory Buttons
        buttonViewLabResults = view.findViewById(R.id.button_view_lab_results);
        buttonRequestLabTest = view.findViewById(R.id.button_request_lab_test);

        // ================= APPOINTMENTS =================

        buttonApproveAppointment.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_doctor, new ApproveAppointmentFragment())
                        .addToBackStack(null)
                        .commit()
        );

        buttonPendingAppointment.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_doctor, new PendingAppointmentFragment())
                        .addToBackStack(null)
                        .commit()
        );

        buttonCompletedAppointment.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_doctor, new CompletedAppointmentFragment())
                        .addToBackStack(null)
                        .commit()
        );

        buttonPatientPrescription.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_doctor, new PatientPrescriptionFragment())
                        .addToBackStack(null)
                        .commit()
        );

        // ================= LABORATORY =================

        if (buttonViewLabResults != null) {
            buttonViewLabResults.setOnClickListener(v ->
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_doctor, new DoctorLabResultsFragment())
                            .addToBackStack(null)
                            .commit()
            );
        }

        if (buttonRequestLabTest != null) {
            buttonRequestLabTest.setOnClickListener(v ->
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_doctor, new RequestLabTestFragment())
                            .addToBackStack(null)
                            .commit()
            );
        }

        return view;
    }
}