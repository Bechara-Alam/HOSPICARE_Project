package com.example.tatwa10.FragmentDoctors;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.Adapters.AppointmentAdapter;
import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private AppointmentAdapter adapter;

    private List<Appointment> historyList = new ArrayList<>();
    private List<Appointment> filteredList = new ArrayList<>();

    private EditText searchPatient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recycler_history);
        searchPatient = view.findViewById(R.id.search_patient_id);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Example data
        historyList.add(new Appointment(
                "P1001",
                "Dr. Smith",
                "12 May 2026",
                "10:00 AM",
                true,
                true,
                "John Smith",
                "Cash"
        ));

        historyList.add(new Appointment(
                "P1002",
                "Dr. Smith",
                "15 May 2026",
                "11:30 AM",
                true,
                true,
                "Anna Brown",
                "Card"
        ));

        historyList.add(new Appointment(
                "P1003",
                "Dr. Smith",
                "20 May 2026",
                "02:00 PM",
                true,
                true,
                "David Lee",
                "Insurance"
        ));

        filteredList.addAll(historyList);

        adapter = new AppointmentAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        // Search listener
        searchPatient.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPatients(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void filterPatients(String text) {

        filteredList.clear();

        for (Appointment item : historyList) {

            if (item.getId().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.notifyDataSetChanged();
    }
}