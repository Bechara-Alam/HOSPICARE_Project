package com.example.tatwa10.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {

    private List<Appointment> appointmentList;

    public AppointmentAdapter(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_item, parent, false);
        return new AppointmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHolder holder, int position) {

        Appointment appointment = appointmentList.get(position);

        holder.textDoctorName.setText(appointment.getDoctorName());
        holder.textDate.setText(appointment.getDate());
        holder.textTime.setText(appointment.getTime());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    static class AppointmentHolder extends RecyclerView.ViewHolder {

        private TextView textDoctorName;
        private TextView textDate;
        private TextView textTime;
        private ImageView imageDoctor;

        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);

            textDoctorName = itemView.findViewById(R.id.textDoctorName);
            textDate = itemView.findViewById(R.id.textDate);
            textTime = itemView.findViewById(R.id.textTime);
            imageDoctor = itemView.findViewById(R.id.imageDoctor);
        }
    }
}