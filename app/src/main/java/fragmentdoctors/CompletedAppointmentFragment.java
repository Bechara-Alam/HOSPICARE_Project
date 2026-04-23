package com.example.tatwa10.FragmentDoctors;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.Adapters.CompletedAppointmentAdapter;
import com.example.tatwa10.ApiService;
import com.example.tatwa10.ModelClass.Appointment;
import com.example.tatwa10.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Type;
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

        recyclerView = view.findViewById(R.id.recycler_view_completed_appointment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CompletedAppointmentAdapter(completedAppointments);
        recyclerView.setAdapter(adapter);

        loadCompleted();

        return view;
    }

    private void loadCompleted() {

        new Thread(() -> {

            try {

                String response = ApiService.getCompletedAppointments();

                Log.d("API_COMPLETED", response);

                if (response == null || response.isEmpty()) {
                    Log.e("API_ERROR", "Completed API empty");
                    return;
                }

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Appointment>>(){}.getType();

                List<Appointment> list = gson.fromJson(response, listType);

                if (list == null) list = new ArrayList<>();

                completedAppointments.clear();
                completedAppointments.addAll(list);

                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());

            } catch (Exception e) {

                e.printStackTrace();
                Log.e("API_ERROR", e.getMessage());

            }

        }).start();
    }

}