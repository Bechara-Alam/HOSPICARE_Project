package com.example.tatwa10.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatwa10.Adapters.PatientAdapter;
import com.example.tatwa10.ApiService;
import com.example.tatwa10.ModelClass.Patient;
import com.example.tatwa10.ModelClass.Prescription;
import com.example.tatwa10.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionFragment extends Fragment {

    private RecyclerView recyclerView;
    private PrescriptionAdapterFrontend adapter;
    private EditText editSearchPatient;
    private RecyclerView recyclerPatients;
    private List<Patient> patientList = new ArrayList<>();
    private int selectedPatientId = -1;
    private TextView textName, textEmail, textPhone;
    private TextView textDisease, textAllergies, textMedications, textAddress;
    private TextView textDob, textNationalId, textBlood;
    private LinearLayout layoutPatientInfo;

    private int currentPatientId = -1;

    public static List<Prescription> prescriptionList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prescription, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_prescription_list);
        editSearchPatient = view.findViewById(R.id.edit_search_patient);
        textName = view.findViewById(R.id.text_name);
        textEmail = view.findViewById(R.id.text_email);
        textPhone = view.findViewById(R.id.text_phone);
        textDob = view.findViewById(R.id.text_dob);
        textNationalId = view.findViewById(R.id.text_national_id);
        textBlood = view.findViewById(R.id.text_blood);
        textAllergies = view.findViewById(R.id.text_allergies);
        textDisease = view.findViewById(R.id.text_disease);
        textMedications = view.findViewById(R.id.text_medications);
        textAddress = view.findViewById(R.id.text_address);
        layoutPatientInfo = view.findViewById(R.id.layout_patient_info);
        FloatingActionButton fab = view.findViewById(R.id.button_add_prescription);

        fab.setOnClickListener(v -> showAddDialog());

        setupRecyclerView();

        // 🔥 Load all at start
        loadAllPrescriptions();
        recyclerPatients = view.findViewById(R.id.recycler_patients);
        recyclerPatients.setLayoutManager(new LinearLayoutManager(getContext()));
        loadPatients();

        // 🔍 SEARCH
        editSearchPatient.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text = s.toString().trim();

                if (text.isEmpty()) {
                    currentPatientId = -1;
                    layoutPatientInfo.setVisibility(View.GONE);
                    loadAllPrescriptions();
                } else {
                    try {
                        currentPatientId = Integer.parseInt(text);

                        loadPatientInfo(currentPatientId);     // 🔥 NEW
                        loadPrescriptions(currentPatientId);   // 🔥 FILTER

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        loadPatients(); // 🔥 refresh patient list
    }
    // =========================
    // 🔥 LOAD ALL PRESCRIPTIONS
    // =========================
    private void loadAllPrescriptions() {

        new Thread(() -> {
            try {

                String response = ApiService.getAllPrescriptions();
                JSONArray array = new JSONArray(response);

                prescriptionList.clear();

                for (int i = 0; i < array.length(); i++) {

                    JSONObject obj = array.getJSONObject(i);

                    Prescription p = new Prescription(
                            "",
                            "Doctor",
                            obj.getString("medicaments"),
                            true, false, true,
                            obj.getString("createdAt"),
                            obj.getString("createdAt"),
                            1,
                            ""
                    );

                    prescriptionList.add(p);
                }

                requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // =========================
    // 🔥 LOAD PRESCRIPTIONS BY PATIENT
    // =========================
    private void loadPrescriptions(int patientId) {

        new Thread(() -> {
            try {

                String response = ApiService.getPrescriptions(patientId);
                JSONArray array = new JSONArray(response);

                prescriptionList.clear();

                for (int i = 0; i < array.length(); i++) {

                    JSONObject obj = array.getJSONObject(i);

                    Prescription p = new Prescription(
                            "",
                            "Doctor",
                            obj.getString("medicaments"),
                            true, false, true,
                            obj.getString("createdAt"),
                            obj.getString("createdAt"),
                            1,
                            ""
                    );

                    prescriptionList.add(p);
                }

                requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // =========================
    // 🔥 LOAD PATIENT INFO
    // =========================
    // =========================
// 🔥 LOAD PATIENT INFO (FIXED)
// =========================
    private void loadPatientInfo(int patientId) {

        new Thread(() -> {
            try {

                // ✅ FIX: correct API
                String response = ApiService.getPatientById(patientId);

                JSONObject obj = new JSONObject(response);

                // ✅ SAFE JSON (no crash)
                String name = obj.optString("FullName", obj.optString("fullName", "N/A"));
                String email = obj.optString("Email", obj.optString("email", "N/A"));
                String phone = obj.optString("Phone", obj.optString("phone", "N/A"));
                String bloodType = obj.optString("BloodType", obj.optString("bloodType", "N/A"));
                String diseases = obj.optString("Diseases", obj.optString("diseases", "N/A"));
                String allergies = obj.optString("Allergies", obj.optString("allergies", "N/A"));
                String medications = obj.optString("Medications", obj.optString("medications", "N/A"));
                String dob = obj.optString("dateOfBirth", "N/A");
                String nationalId = obj.optString("nationalId", "N/A");

// clean date
                if (dob != null && dob.contains("T")) {
                    dob = dob.split("T")[0];
                }

// null safety
                if (dob == null || dob.equals("null") || dob.isEmpty()) {
                    dob = "N/A";
                }

                if (nationalId == null || nationalId.equals("null") || nationalId.isEmpty()) {
                    nationalId = "N/A";
                }

// ✅ SET TEXT CORRECTLY
                textDob.setText("DOB: " + dob);
                textNationalId.setText("ID: " + nationalId);
                String address = obj.optString("Address", obj.optString("address", "N/A"));
                String country = obj.optString("Country", obj.optString("country", "N/A"));
                String city = obj.optString("City", obj.optString("city", "N/A"));
                requireActivity().runOnUiThread(() -> {

                    layoutPatientInfo.setVisibility(View.VISIBLE);

                    textName.setText("Name: " + name);
                    textEmail.setText("Email: " + email);
                    textPhone.setText("Phone: " + phone);

                    textBlood.setText("Blood Type: " + bloodType);
                    textAllergies.setText("Allergies: " + allergies);
                    textDisease.setText("Diseases: " + diseases);
                    textMedications.setText("Medications: " + medications);

                    textAddress.setText("Address: " + address + ", " + city + ", " + country);


                });

            } catch (Exception e) {
                e.printStackTrace();

                requireActivity().runOnUiThread(() -> {
                    layoutPatientInfo.setVisibility(View.VISIBLE);
                    textName.setText("Patient not found");
                    textEmail.setText("");
                    textPhone.setText("");
                });
            }
        }).start();
    }

    // =========================
    // ➕ ADD PRESCRIPTION DIALOG
    // =========================
    private void showAddDialog() {

        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_prescription, null);

        EditText patientIdInput = dialogView.findViewById(R.id.edit_patient_id);
        EditText medicineInput = dialogView.findViewById(R.id.edit_medicine);
        EditText notesInput = dialogView.findViewById(R.id.edit_notes);

        if (selectedPatientId != -1) {
            patientIdInput.setText(String.valueOf(selectedPatientId));
        }

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Add Prescription")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {

                    String patientIdStr = patientIdInput.getText().toString();
                    String medicine = medicineInput.getText().toString();
                    String notes = notesInput.getText().toString();

                    if (!patientIdStr.isEmpty() && !medicine.isEmpty()) {

                        int patientId;

                        if (selectedPatientId != -1) {
                            patientId = selectedPatientId; // 🔥 ALWAYS USE SELECTED
                        } else {
                            patientId = Integer.parseInt(patientIdStr); // fallback
                        }
                        savePrescription(patientId, medicine, notes);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // =========================
    // 💾 SAVE PRESCRIPTION
    // =========================
    private void savePrescription(int patientId, String medicine, String notes) {

        new Thread(() -> {
            try {

                JSONObject json = new JSONObject();
                json.put("patientId", patientId);
                json.put("doctorId", 1);
                json.put("medicaments", medicine);
                json.put("notes", notes);

                ApiService.addPrescription(json.toString());

                if (currentPatientId == -1) {
                    loadAllPrescriptions();
                } else {
                    loadPrescriptions(currentPatientId);
                }

                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show()
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // =========================
    // 🔧 SETUP RECYCLER
    // =========================
    private void setupRecyclerView() {

        adapter = new PrescriptionAdapterFrontend(prescriptionList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    private void loadPatients() {

        new Thread(() -> {

            try {

                String response = ApiService.getPatients();

                // 🔥 DEBUG (check Logcat)
                android.util.Log.d("PATIENT_API", "Response = " + response);

                requireActivity().runOnUiThread(() -> {

                    try {

                        // ❌ CHECK EMPTY OR ERROR
                        if (response == null || response.isEmpty()) {
                            Toast.makeText(getContext(), "Empty response from server", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // ❌ CHECK IF NOT JSON ARRAY
                        if (!response.trim().startsWith("[")) {
                            Toast.makeText(getContext(), "Invalid response", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        JSONArray array = new JSONArray(response);

                        patientList.clear();

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject obj = array.getJSONObject(i);

                            // ✅ SAFE JSON (no crash)
                            int id = obj.optInt("patientId", -1);
                            String name = obj.optString("fullName", "Unknown");

                            if (id != -1) {
                                patientList.add(new Patient(id, name));
                            }
                        }

                        // ✅ ADAPTER
                        PatientAdapter adapter = new PatientAdapter(patientList, patient -> {

                            selectedPatientId = patient.getPatientId();
                            currentPatientId = selectedPatientId;

                            // 🔥 AUTO FILL SEARCH
                            editSearchPatient.setText(String.valueOf(selectedPatientId));

                            Toast.makeText(getContext(),
                                    "Selected: " + patient.getFullName(),
                                    Toast.LENGTH_SHORT).show();
                        });

                        recyclerPatients.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Parsing error", Toast.LENGTH_SHORT).show();
                    }

                });

            } catch (Exception e) {
                e.printStackTrace();

                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Connection error", Toast.LENGTH_SHORT).show()
                );
            }

        }).start();
    }
}