package com.example.tatwa10.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.ModelClass.Prescription;
import com.example.tatwa10.R;

import java.util.List;

public class CompletedAppointmentAdapter
        extends RecyclerView.Adapter<CompletedAppointmentAdapter.CompletedHolder> {

    private List<Appointment> appointmentList;

    public CompletedAppointmentAdapter(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public CompletedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.completed_appointment_item, parent, false);
        return new CompletedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedHolder holder, int position) {

        Appointment appointment = appointmentList.get(position);

        holder.name.setText(appointment.getName());
        holder.contact.setText(appointment.getId());
        holder.date.setText(appointment.getAppointmentDate());
        holder.time.setText(appointment.getAppointmentTime());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    static class CompletedHolder extends RecyclerView.ViewHolder {

        TextView name, contact, date, time;

        public CompletedHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.text_view_item_completed_appointment_patient_name);
            contact = itemView.findViewById(R.id.text_view_item_completed_patient_contact);
            date = itemView.findViewById(R.id.text_view_item_completed_appointment_date);
            time = itemView.findViewById(R.id.text_view_item_completed_appointment_time);
        }
    }

    public static class PrescriptionAdapterFrontend
            extends RecyclerView.Adapter<PrescriptionAdapterFrontend.ViewHolder> {

        private List<Prescription> list;

        public PrescriptionAdapterFrontend(List<Prescription> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.prescription_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Prescription p = list.get(position);

            holder.textViewDoctorName.setText("Prescribed by Dr. " + p.getDoctorName());
            holder.textViewMedicineName.setText(p.getMedicineName());

            holder.textViewDateStart.setText(p.getDateStart());
            holder.textViewDateEnd.setText(p.getDateEnd());

            holder.textViewDuration.setText("Duration: " + p.getDuration() + " Days");

            // Hide chips first
            holder.chipBreakfast.setVisibility(View.GONE);
            holder.chipLunch.setVisibility(View.GONE);
            holder.chipDinner.setVisibility(View.GONE);

            // Show chips based on prescription schedule
            if (p.isBreakfast()) holder.chipBreakfast.setVisibility(View.VISIBLE);
            if (p.isLunch()) holder.chipLunch.setVisibility(View.VISIBLE);
            if (p.isDinner()) holder.chipDinner.setVisibility(View.VISIBLE);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            TextView textViewDoctorName;
            TextView textViewMedicineName;
            TextView textViewDateStart;
            TextView textViewDateEnd;
            TextView textViewDuration;

            Chip chipBreakfast;
            Chip chipLunch;
            Chip chipDinner;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textViewDoctorName = itemView.findViewById(R.id.text_view_doctor_prescribed_by);
                textViewMedicineName = itemView.findViewById(R.id.text_view_medicine_name);

                textViewDateStart = itemView.findViewById(R.id.text_view_date_start);
                textViewDateEnd = itemView.findViewById(R.id.text_view_date_end);
                textViewDuration = itemView.findViewById(R.id.text_view_item_medicine_duration);

                chipBreakfast = itemView.findViewById(R.id.button_breakfast);
                chipLunch = itemView.findViewById(R.id.button_lunch);
                chipDinner = itemView.findViewById(R.id.button_dinner);
            }
        }
    }
}