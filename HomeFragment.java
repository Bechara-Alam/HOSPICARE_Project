package com.example.tatwa10.Fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.tatwa10.ApiService;
import com.example.tatwa10.FragmentDoctors.PatientPrescriptionFragment;
import com.example.tatwa10.MainActivity;
import com.example.tatwa10.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    // ✅ FIXED: use View instead of Button
    private View buttonFindDoctors;
    private View buttonBookAppointment;
    private View buttonPrescriptions;
    private View buttonCallAmbulance;
    private View buttonNotifications;
    private View buttonBookLab;
    private View buttonMedicalRecords;

    private View notificationBadge;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        MainActivity.navigationView.setCheckedItem(R.id.nav_home);
        MainActivity.currentFragment = "home";

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // ✅ Bind views
        buttonFindDoctors = view.findViewById(R.id.button_find_doctors);
        buttonBookAppointment = view.findViewById(R.id.button_book_appointment);
        buttonPrescriptions = view.findViewById(R.id.button_view_prescriptions);
        buttonCallAmbulance = view.findViewById(R.id.button_emergency);
        buttonNotifications = view.findViewById(R.id.button_notifications);
        buttonBookLab = view.findViewById(R.id.button_book_lab);
        buttonMedicalRecords = view.findViewById(R.id.button_medical_records);

        notificationBadge = view.findViewById(R.id.notification_badge);
        notificationBadge.setVisibility(View.GONE);

        // ✅ Click listeners

        buttonFindDoctors.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new FindDoctorsFragment())
                        .addToBackStack(null)
                        .commit()
        );

        buttonBookAppointment.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new BookAppointmentFragment())
                        .addToBackStack(null)
                        .commit()
        );

        buttonPrescriptions.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new PatientPrescriptionFragment())
                        .addToBackStack(null)
                        .commit()
        );

        buttonCallAmbulance.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new CallAmbulanceFragment())
                        .addToBackStack(null)
                        .commit()
        );



        buttonBookLab.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new BookLabTestFragment())
                        .addToBackStack(null)
                        .commit()
        );

        buttonNotifications.setOnClickListener(v -> {

            SharedPreferences prefs =
                    requireActivity().getSharedPreferences("notifications", Context.MODE_PRIVATE);

            int latestNotificationId = prefs.getInt("latestNotificationId", -1);

            prefs.edit()
                    .putInt("lastOpenedNotificationId", latestNotificationId)
                    .apply();

            notificationBadge.setVisibility(View.GONE);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new NotificationsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs =
                requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        int patientId = prefs.getInt("patientId", 0);

        if (patientId != 0) {
            startNotificationChecker(patientId);
        }
    }

    private void showNotification(String message) {
        NotificationManager manager =
                (NotificationManager) requireActivity()
                        .getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "appointment_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Hospital Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(requireContext(), channelId)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Hospital App")
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);

        manager.notify(100, builder.build());
    }

    private void startNotificationChecker(int patientId) {

        new Thread(() -> {

            while (true) {

                try {

                    String response = ApiService.getNotifications(patientId);

                    JSONArray array = new JSONArray(response);

                    if (array.length() > 0) {

                        JSONObject latest = array.getJSONObject(0);

                        int notificationId = latest.getInt("id");
                        String message = latest.getString("message");

                        SharedPreferences prefs =
                                requireActivity().getSharedPreferences("notifications", Context.MODE_PRIVATE);

                        int lastId = prefs.getInt("lastNotificationId", -1);

                        if (notificationId != lastId) {

                            prefs.edit().putInt("lastNotificationId", notificationId).apply();

                            requireActivity().runOnUiThread(() -> {
                                notificationBadge.setVisibility(View.VISIBLE);
                                showNotification(message);
                            });
                        }
                    }

                    Thread.sleep(5000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}