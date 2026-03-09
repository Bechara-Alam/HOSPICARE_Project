package com.example.tatwa10.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.Adapters.PrescriptionAdapterFrontend;
import com.example.tatwa10.ModelClass.Prescription;
import com.example.tatwa10.R;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionFragment extends Fragment {

    private RecyclerView recyclerView;
    private PrescriptionAdapterFrontend adapter;

    // ⭐ Local demo data (NO FIREBASE)
    public static List<Prescription> prescriptionList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prescription, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_prescription_list);

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {

        // 🔹 Add demo data only once
        if (prescriptionList.isEmpty()) {

            prescriptionList.add(new Prescription(
                    "+961000000",
                    "Dr. Ahmad",
                    "Paracetamol",
                    true,
                    false,
                    true,
                    "01 Mar 2026",
                    "05 Mar 2026",
                    5,
                    "John Doe"
            ));

            prescriptionList.add(new Prescription(
                    "+961000001",
                    "Dr. Sara",
                    "Ibuprofen",
                    true,
                    true,
                    false,
                    "10 Mar 2026",
                    "15 Mar 2026",
                    6,
                    "Jane Doe"
            ));
        }

        adapter = new PrescriptionAdapterFrontend(prescriptionList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}
