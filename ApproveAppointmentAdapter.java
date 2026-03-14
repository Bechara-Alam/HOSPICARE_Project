package com.example.tatwa10.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ApproveAppointmentAdapter
        extends RecyclerView.Adapter<ApproveAppointmentAdapter.AppointmentHolder> {

    private List<Appointment> appointmentList;
    private OnItemClickListener listener;

    public ApproveAppointmentAdapter(List<Appointment> appointmentList,
                                     OnItemClickListener listener) {
        this.appointmentList = appointmentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approve_appointment_item, parent, false);
        return new AppointmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHolder holder, int position) {

        Appointment appointment = appointmentList.get(position);

        // Patient name
        holder.textViewPatientName.setText(appointment.getName());

        // Contact number
        holder.textViewContactNo.setText("Contact · " + appointment.getId());

        // Date + Time combined
        String dateTime = appointment.getAppointmentDate()
                + " · " + appointment.getAppointmentTime();
        holder.textViewDateTime.setText(dateTime);

        // Status
        holder.textViewStatus.setText("Pending");

        // ⭐ SHOW INITIALS AVATAR
        String initials = getInitials(appointment.getName());
        holder.textAvatar.setText(initials);
        holder.textAvatar.getBackground().setTint(getColorFromName(appointment.getName()));
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    class AppointmentHolder extends RecyclerView.ViewHolder {

        TextView textViewPatientName;
        TextView textViewContactNo;
        TextView textViewDateTime;
        TextView textViewStatus;
        TextView textAvatar;   // ⭐ NEW

        MaterialButton buttonAccept, buttonReject;

        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);

            textViewPatientName =
                    itemView.findViewById(R.id.text_view_item_approve_appointment_patient_name);

            textViewContactNo =
                    itemView.findViewById(R.id.text_view_item_approve_patient_contact);

            textViewDateTime =
                    itemView.findViewById(R.id.text_view_item_approve_appointment_datetime);

            textViewStatus =
                    itemView.findViewById(R.id.chip_status);

            textAvatar =                      // ⭐ NEW
                    itemView.findViewById(R.id.text_avatar);

            buttonAccept =
                    itemView.findViewById(R.id.button_item_accept_appointment);

            buttonReject =
                    itemView.findViewById(R.id.button_item_reject_appointment);

            buttonAccept.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onAcceptClick(pos);
                }
            });

            buttonReject.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onRejectClick(pos);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onAcceptClick(int position);
        void onRejectClick(int position);
    }

    // ================= INITIALS METHODS =================

    private String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) return "?";

        String[] parts = name.trim().split(" ");

        if (parts.length == 1)
            return parts[0].substring(0, 1).toUpperCase();

        return (parts[0].substring(0, 1)
                + parts[1].substring(0, 1)).toUpperCase();
    }

    private int getColorFromName(String name) {
        int[] colors = {
                0xFFEF4444,
                0xFF3B82F6,
                0xFF10B981,
                0xFFF59E0B,
                0xFF8B5CF6,
                0xFFEC4899
        };

        return colors[Math.abs(name.hashCode()) % colors.length];
    }
}