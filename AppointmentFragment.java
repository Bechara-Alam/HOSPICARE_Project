package com.example.tatwa10.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.AppointmentStorage;
import com.example.tatwa10.Adapters.AppointmentAdapter;
import com.example.tatwa10.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton buttonAddAppointment;
    private AppointmentAdapter adapter;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        recyclerView = view.findViewById(R.id.recycler_view_appointment_list);
        buttonAddAppointment = view.findViewById(R.id.button_add_appointment);

        buttonAddAppointment.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new BookAppointmentFragment())
                        .addToBackStack(null)
                        .commit()
        );

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AppointmentAdapter(AppointmentStorage.appointmentList);
        recyclerView.setAdapter(adapter);

        dialog.dismiss();
    }
}