package com.example.tatwa10.FragmentDoctors;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.Adapters.PendingAppointmentAdapter;
import com.example.tatwa10.AppointmentStorage;
import com.example.tatwa10.DoctorMainActivity;
import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;

import java.util.ArrayList;
import java.util.List;

public class PendingAppointmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private PendingAppointmentAdapter adapter;

    private List<Appointment> pendingAppointments;
    private List<Appointment> completedAppointments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pending_appointments, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_pending_appointment);

        buildRecyclerView();

        return view;
    }

    private void buildRecyclerView() {

        pendingAppointments = new ArrayList<>();
        completedAppointments = new ArrayList<>();

        // 🔹 Get accepted appointments for this doctor
        for (Appointment appointment : AppointmentStorage.appointmentList) {

            if (appointment.getDoctorName() != null &&
                    appointment.getDoctorName().equals(DoctorMainActivity.doctorName) &&
                    appointment.isAppointmentAccepted()) {

                pendingAppointments.add(appointment);
            }
        }

        adapter = new PendingAppointmentAdapter(pendingAppointments,
                new PendingAppointmentAdapter.OnItemClickListener() {

                    @Override
                    public void onCallClick(String number) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + number));
                        startActivity(callIntent);
                    }

                    @Override
                    public void onMessageClick(String number) {
                        Intent messageIntent = new Intent(Intent.ACTION_VIEW);
                        messageIntent.setData(Uri.parse("sms:" + number));
                        startActivity(messageIntent);
                    }

                    @Override
                    public void onCompletedClick(Appointment appointment, int position) {

                        appointment.setAppointmentDone(true);

                        pendingAppointments.remove(position);
                        adapter.notifyItemRemoved(position);

                        completedAppointments.add(appointment);
                    }
                });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}