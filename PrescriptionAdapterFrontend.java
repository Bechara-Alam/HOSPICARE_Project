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

public class PrescriptionAdapterFrontend
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

        holder.textViewDoctorName.setText(p.getDoctorName());
        holder.textViewMedicineName.setText(p.getMedicineName());
        holder.textViewDateStart.setText(p.getDateStart());
        holder.textViewDateEnd.setText(p.getDateEnd());
        holder.textViewDuration.setText(p.getDuration() + " Days");

        if (p.isBreakfast()) holder.buttonBreakfast.setVisibility(View.VISIBLE);
        if (p.isLunch()) holder.buttonLunch.setVisibility(View.VISIBLE);
        if (p.isDinner()) holder.buttonDinner.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDoctorName, textViewMedicineName,
                textViewDateStart, textViewDateEnd, textViewDuration;
        Button buttonBreakfast, buttonLunch, buttonDinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDoctorName = itemView.findViewById(R.id.text_view_doctor_prescribed_by);
            textViewMedicineName = itemView.findViewById(R.id.text_view_medicine_name);
            buttonBreakfast = itemView.findViewById(R.id.button_breakfast);
            buttonLunch = itemView.findViewById(R.id.button_lunch);
            buttonDinner = itemView.findViewById(R.id.button_dinner);
            textViewDateStart = itemView.findViewById(R.id.text_view_date_start);
            textViewDateEnd = itemView.findViewById(R.id.text_view_date_end);
            textViewDuration = itemView.findViewById(R.id.text_view_item_medicine_duration);
        }
    }
}
