package com.example.tatwa10.FragmentDoctors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.Adapters.PatientPrescriptionAdapter;
import com.example.tatwa10.DoctorMainActivity;
import com.example.tatwa10.ModelClass.Prescription;
import com.example.tatwa10.R;

import java.util.ArrayList;
import java.util.List;

public class PatientPrescriptionFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientPrescriptionAdapter adapter;
    private List<Prescription> prescriptionList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        DoctorMainActivity.navigationView.setCheckedItem(R.id.nav_prescription2);
        DoctorMainActivity.currentFragment = "patient_prescription";

        View view = inflater.inflate(R.layout.fragment_patient_prescription, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_prescription_list);

        buildRecyclerView();

        return view;
    }

    private void buildRecyclerView() {

        prescriptionList = new ArrayList<>();

        prescriptionList.add(new Prescription(
                "John",
                DoctorMainActivity.doctorName,
                "Paracetamol",
                true, false, true,
                "01/03/2026",
                "05/03/2026",
                5
        ));

        prescriptionList.add(new Prescription(
                "Sara",
                DoctorMainActivity.doctorName,
                "Ibuprofen",
                false, true, true,
                "10/03/2026",
                "15/03/2026",
                6
        ));

        adapter = new PatientPrescriptionAdapter(prescriptionList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}