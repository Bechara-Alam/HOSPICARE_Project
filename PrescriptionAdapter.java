package com.example.tatwa10.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.Prescription;
import com.example.tatwa10.R;

import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder> {

    private List<Prescription> prescriptionList;
    private OnItemClickListener listener;

    // 🔹 Constructor with local list
    public PrescriptionAdapter(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    // 🔹 Set click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prescription_item, parent, false);
        return new PrescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {

        Prescription prescription = prescriptionList.get(position);

        holder.textViewDoctorName.setText(prescription.getDoctorName());
        holder.textViewMedicineName.setText(prescription.getMedicineName());

        // Reset visibility first (important for RecyclerView reuse)
        holder.buttonBreakfast.setVisibility(View.GONE);
        holder.buttonLunch.setVisibility(View.GONE);
        holder.buttonDinner.setVisibility(View.GONE);

        if (prescription.isBreakfast()) holder.buttonBreakfast.setVisibility(View.VISIBLE);
        if (prescription.isLunch()) holder.buttonLunch.setVisibility(View.VISIBLE);
        if (prescription.isDinner()) holder.buttonDinner.setVisibility(View.VISIBLE);

        holder.textViewDateStart.setText(prescription.getDateStart());
        holder.textViewDateEnd.setText(prescription.getDateEnd());

        String duration = prescription.getDuration() + " Days";
        holder.textViewDuration.setText(duration);
    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }

    // 🔹 ViewHolder
    class PrescriptionViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDoctorName;
        private TextView textViewMedicineName;
        private Button buttonBreakfast;
        private Button buttonLunch;
        private Button buttonDinner;
        private TextView textViewDateStart;
        private TextView textViewDateEnd;
        private TextView textViewDuration;

        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDoctorName = itemView.findViewById(R.id.text_view_doctor_prescribed_by);
            textViewMedicineName = itemView.findViewById(R.id.text_view_medicine_name);
            buttonBreakfast = itemView.findViewById(R.id.button_breakfast);
            buttonLunch = itemView.findViewById(R.id.button_lunch);
            buttonDinner = itemView.findViewById(R.id.button_dinner);
            textViewDateStart = itemView.findViewById(R.id.text_view_date_start);
            textViewDateEnd = itemView.findViewById(R.id.text_view_date_end);
            textViewDuration = itemView.findViewById(R.id.text_view_item_medicine_duration);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(prescriptionList.get(position), position);
                }
            });
        }
    }

    // 🔹 Clean interface WITHOUT Firebase
    public interface OnItemClickListener {
        void onItemClick(Prescription prescription, int position);
    }
}
