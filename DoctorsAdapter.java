package com.example.tatwa10.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.ModelClass.Doctor;
import com.example.tatwa10.R;

import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {

    private List<Doctor> doctorList;
    private Context context;
    private OnItemClickListener listener;

    public DoctorsAdapter(Context context) {
        this.context = context;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_item, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);

        holder.textDoctorName.setText(doctor.getName());
        holder.textDoctorField.setText(doctor.getSpecification());
    }

    @Override
    public int getItemCount() {
        return doctorList == null ? 0 : doctorList.size();
    }

    class DoctorViewHolder extends RecyclerView.ViewHolder {

        TextView textDoctorName;
        TextView textDoctorField;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            textDoctorName = itemView.findViewById(R.id.text_doctor_name);
            textDoctorField = itemView.findViewById(R.id.text_doctor_field);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(doctorList.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Doctor doctor);
    }
}
