package com.example.tatwa10.FragmentDoctors;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.Adapters.ApproveAppointmentAdapter;
import com.example.tatwa10.AppointmentStorage;
import com.example.tatwa10.DoctorMainActivity;
import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;

import java.util.ArrayList;
import java.util.List;

public class ApproveAppointmentFragment extends Fragment {

    private RecyclerView recyclerViewAppointmentList;
    private ApproveAppointmentAdapter adapter;
    private List<Appointment> appointmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_approve_appointment, container, false);

        recyclerViewAppointmentList = view.findViewById(R.id.recycler_view_approve_appointment);

        buildRecyclerView();

        return view;
    }

    private void buildRecyclerView() {

        appointmentList = new ArrayList<>();

        // Make sure storage is not null
        if (AppointmentStorage.appointmentList != null) {

            for (Appointment appointment : AppointmentStorage.appointmentList) {

                if (appointment.getDoctorName() != null &&
                        appointment.getDoctorName().equals(DoctorMainActivity.doctorName)) {

                    appointmentList.add(appointment);
                }
            }
        }

        adapter = new ApproveAppointmentAdapter(appointmentList,
                new ApproveAppointmentAdapter.OnItemClickListener() {

                    @Override
                    public void onAcceptClick(int position) {

                        if (getContext() == null) return;

                        new AlertDialog.Builder(getContext())
                                .setTitle("Accept")
                                .setMessage("Are you sure you want to accept this appointment?")
                                .setPositiveButton("Yes", (dialog, which) -> {

                                    appointmentList.get(position).setAppointmentAccepted(true);
                                    adapter.notifyItemChanged(position);

                                    Toast.makeText(getContext(),
                                            "Appointment Accepted",
                                            Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }

                    @Override
                    public void onRejectClick(int position) {

                        if (getContext() == null) return;

                        new AlertDialog.Builder(getContext())
                                .setTitle("Reject")
                                .setMessage("Are you sure you want to reject this appointment?")
                                .setPositiveButton("Yes", (dialog, which) -> {

                                    appointmentList.remove(position);
                                    adapter.notifyItemRemoved(position);

                                    Toast.makeText(getContext(),
                                            "Appointment Rejected",
                                            Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                });

        recyclerViewAppointmentList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAppointmentList.setAdapter(adapter);
    }
}