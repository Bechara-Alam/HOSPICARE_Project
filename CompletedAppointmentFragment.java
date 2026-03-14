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

import com.example.tatwa10.Adapters.CompletedAppointmentAdapter;
import com.example.tatwa10.Data.AppointmentRepository;
import com.example.tatwa10.DoctorMainActivity;
import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;

import java.util.ArrayList;
import java.util.List;

public class CompletedAppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private CompletedAppointmentAdapter adapter;

    private List<Appointment> completedAppointments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_completed_appointment, container, false);

        // Set navigation state
        DoctorMainActivity.navigationView.setCheckedItem(R.id.nav_completed_appointment2);
        DoctorMainActivity.currentFragment = "completed_appointment";

        recyclerView = view.findViewById(R.id.recycler_view_completed_appointment);

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {

        // Get list from repository safely
        if (AppointmentRepository.completedAppointments != null) {
            completedAppointments = AppointmentRepository.completedAppointments;
        }

        adapter = new CompletedAppointmentAdapter(completedAppointments);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh list when returning to this screen
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}