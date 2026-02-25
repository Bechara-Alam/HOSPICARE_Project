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

import java.util.List;

public class CompletedAppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private CompletedAppointmentAdapter adapter;

    // 🔹 Shared list from repository
    private List<Appointment> completedAppointments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        DoctorMainActivity.navigationView.setCheckedItem(R.id.nav_completed_appointment2);
        DoctorMainActivity.currentFragment = "completed_appointment";

        View view = inflater.inflate(R.layout.fragment_completed_appointment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_completed_appointment);

        buildRecyclerView();

        return view;
    }

    private void buildRecyclerView() {

        // ✅ Use shared repository list (IMPORTANT)
        completedAppointments = AppointmentRepository.completedAppointments;

        adapter = new CompletedAppointmentAdapter(completedAppointments);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    // 🔹 Refresh list when fragment becomes visible
    @Override
    public void onResume() {
        super.onResume();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}