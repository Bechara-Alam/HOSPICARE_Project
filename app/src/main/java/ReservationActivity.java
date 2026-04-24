package com.example.tatwa10;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tatwa10.Adapters.RoomAdapter;
import com.example.tatwa10.ModelClass.Patient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.*;

public class ReservationActivity extends AppCompatActivity {

    private Spinner spinnerPatients;
    private GridView gridRooms;
    private Button btnReserve;

    private EditText editDate, editStartTime, editEndTime;

    private TextView textSelectedRoom, textSelectedPatient, textSelectedDate;
    private TextView textStartTime, textEndTime;

    private List<Room> roomObjects = new ArrayList<>();
    private List<Patient> patientList = new ArrayList<>();

    private Room selectedRoom = null;

    private String startTime = "";
    private String endTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        spinnerPatients = findViewById(R.id.spinner_patients);
        gridRooms = findViewById(R.id.grid_rooms);
        btnReserve = findViewById(R.id.btn_reserve);

        editDate = findViewById(R.id.edit_date);
        editStartTime = findViewById(R.id.edit_start_time);
        editEndTime = findViewById(R.id.edit_end_time);

        textSelectedRoom = findViewById(R.id.text_selected_room);
        textSelectedPatient = findViewById(R.id.text_selected_patient);
        textSelectedDate = findViewById(R.id.text_selected_date);

        textStartTime = findViewById(R.id.edit_start_time);
        textEndTime = findViewById(R.id.edit_end_time);

        // ✅ CLICK EVENTS
        editDate.setOnClickListener(v -> showDatePicker());
        editStartTime.setOnClickListener(v -> pickTime(true));
        editEndTime.setOnClickListener(v -> pickTime(false));

        loadRooms();
        loadPatients();

        spinnerPatients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {

                if (position == 0) {
                    textSelectedPatient.setText("Patient: Not selected");
                    return;
                }

                Patient p = patientList.get(position - 1);

                String info = "Patient: " + p.getFullName();

                if (p.getPhone() != null)
                    info += "\nPhone: " + p.getPhone();

                if (p.getBloodType() != null)
                    info += "\nBlood: " + p.getBloodType();

                if (p.getAddress() != null)
                    info += "\nAddress: " + p.getAddress();

                textSelectedPatient.setText(info);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textSelectedPatient.setText("Patient: Not selected");
            }
        });

        btnReserve.setOnClickListener(v -> reserveRoom());
    }
    public void onRoomSelected(Room room) {
        selectedRoom = room;
        textSelectedRoom.setText("Room: " + room.getName());
    }
    // ===============================
    // DATE PICKER
    // ===============================
    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, day) -> {

                    String date = day + "/" + (month + 1) + "/" + year;

                    editDate.setText(date);
                    textSelectedDate.setText("Date: " + date);

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    // ===============================
    // TIME PICKER
    // ===============================
    private void pickTime(boolean isStart) {

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {

                    String time = selectedHour + ":" + String.format("%02d", selectedMinute);

                    if (isStart) {
                        startTime = time;
                        editStartTime.setText(time);
                        textStartTime.setText("Start: " + time);
                    } else {
                        endTime = time;
                        editEndTime.setText(time);
                        textEndTime.setText("End: " + time);
                    }

                }, hour, minute, true);

        timePicker.show();
    }

    // ===============================
    // VALIDATE TIME
    // ===============================
    private boolean isValidTimeRange() {
        try {
            String[] s = startTime.split(":");
            String[] e = endTime.split(":");

            int start = Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1]);
            int end = Integer.parseInt(e[0]) * 60 + Integer.parseInt(e[1]);

            return end > start;

        } catch (Exception ex) {
            return false;
        }
    }

    // ===============================
    // LOAD ROOMS
    // ===============================
    public void loadRooms() {

        new Thread(() -> {
            try {

                String response = ApiService.getRooms();

                if (response == null || response.isEmpty()) {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Failed to load rooms ❌", Toast.LENGTH_SHORT).show()
                    );
                    return;
                }

                Room[] rooms = new Gson().fromJson(response, Room[].class);

                runOnUiThread(() -> {

                    roomObjects.clear();

                    if (rooms != null) {
                        Collections.addAll(roomObjects, rooms);
                    }

                    // ✅ Set adapter
                    RoomAdapter adapter = new RoomAdapter(this, roomObjects);
                    gridRooms.setAdapter(adapter);

// ✅ ADD THIS LINE

                });

            } catch (Exception e) {

                runOnUiThread(() ->
                        Toast.makeText(this, "Error loading rooms ❌", Toast.LENGTH_SHORT).show()
                );

                e.printStackTrace();
            }
        }).start();
    }

    // ===============================
    // LOAD PATIENTS
    // ===============================
    private void loadPatients() {

        new Thread(() -> {
            try {

                String response = ApiService.getPatients();
                Patient[] patients = new Gson().fromJson(response, Patient[].class);

                runOnUiThread(() -> {

                    patientList.clear();

                    if (patients != null) {
                        Collections.addAll(patientList, patients);
                    }

                    List<String> names = new ArrayList<>();
                    names.add("  Select Patient ");

                    for (Patient p : patientList) {
                        names.add(p.getFullName());
                    }

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(this,
                                    android.R.layout.simple_spinner_item,
                                    names);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerPatients.setAdapter(adapter);

                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // ===============================
    // RESERVE ROOM
    // ===============================
    private void reserveRoom() {

        String date = editDate.getText().toString().trim();

        if (selectedRoom == null) {
            Toast.makeText(this, "Select a room first", Toast.LENGTH_SHORT).show();
            return;
        }

        if (date.isEmpty()) {
            Toast.makeText(this, "Select date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (startTime.isEmpty() || endTime.isEmpty()) {
            Toast.makeText(this, "Select start & end time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidTimeRange()) {
            Toast.makeText(this, "End time must be after start time ❌", Toast.LENGTH_SHORT).show();
            return;
        }

        int position = spinnerPatients.getSelectedItemPosition();

        if (position == 0) {
            Toast.makeText(this, "Please select a patient ❌", Toast.LENGTH_SHORT).show();
            return;
        }

        Patient selectedPatient = patientList.get(position - 1);

        // ✅ OPTIONAL: prevent selecting already reserved room
        if (selectedRoom.isReserved()) {
            Toast.makeText(this, "Room already reserved ❌", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try {

                JSONObject json = new JSONObject();

                json.put("roomId", selectedRoom.getId());
                json.put("patientName", selectedPatient.getFullName());

// ✅ ADD THIS (IMPORTANT)
                json.put("reservationDate", date);

// ✅ FORMAT DATE FOR BACKEND
                try {
                    java.text.SimpleDateFormat inputFormat =
                            new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault());

                    java.text.SimpleDateFormat outputFormat =
                            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());

                    java.util.Date start = inputFormat.parse(date + " " + startTime);
                    java.util.Date end = inputFormat.parse(date + " " + endTime);

                    // ✅ USE formatted dates (THIS WAS MISSING)
                    String startDateTime = outputFormat.format(start);
                    String endDateTime = outputFormat.format(end);

                    json.put("startDateTime", startDateTime);
                    json.put("endDateTime", endDateTime);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                json.put("paymentStatus", "paid");

// ✅ ADD THESE (VERY IMPORTANT)
                json.put("phone", selectedPatient.getPhone());
                json.put("bloodType", selectedPatient.getBloodType());
                json.put("address", selectedPatient.getAddress());

                // ✅ IMPORTANT: get response
                String response = ApiService.reserveRoom(json.toString());

                runOnUiThread(() -> {

                    // ✅ CHECK RESPONSE (VERY IMPORTANT)
                    if (response != null && response.contains("❌")) {
                        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                        return;
                    }

                    Toast.makeText(this, "Reservation Successful ✅", Toast.LENGTH_LONG).show();

                    // 🔄 RESET EVERYTHING
                    editDate.setText("");
                    editStartTime.setText("");
                    editEndTime.setText("");

                    textSelectedDate.setText("Date: Not selected");
                    textStartTime.setText("Start: Not selected");
                    textEndTime.setText("End: Not selected");

                    textSelectedRoom.setText("Room: Not selected");
                    textSelectedPatient.setText("Patient: Not selected");

                    spinnerPatients.setSelection(0);
                    selectedRoom = null;

                    startTime = "";
                    endTime = "";

                    new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                        loadRooms();
                    }, 300);

                });

            } catch (Exception e) {

                runOnUiThread(() ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );

                e.printStackTrace();
            }
        }).start();
    }

    // ===============================
    // HIGHLIGHT
    // ===============================
    private void highlightSelection(int selectedPosition) {

        for (int i = 0; i < gridRooms.getChildCount(); i++) {

            LinearLayout layout = (LinearLayout) gridRooms.getChildAt(i);

            if (i == selectedPosition) {
                layout.setBackgroundColor(Color.parseColor("#BBDEFB"));
            } else {
                layout.setBackgroundColor(Color.WHITE);
            }
        }
    }
    // ===============================
// FIX GRID HEIGHT (IMPORTANT)
// ===============================

}