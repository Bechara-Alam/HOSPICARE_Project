package com.example.tatwa10;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tatwa10.Adapters.RoomAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservationActivity extends AppCompatActivity {

    private EditText editPatientName, editDate;
    private GridView gridRooms;
    private Button btnReserve;

    private List<Room> roomObjects = new ArrayList<>();
    private Room selectedRoom = null; // 🔥 selected room
    String stage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        // ✅ MATCH XML
        editPatientName = findViewById(R.id.edit_patient_name);
        editDate = findViewById(R.id.edit_date);
        gridRooms = findViewById(R.id.grid_rooms);
        btnReserve = findViewById(R.id.btn_reserve);

        stage = getIntent().getStringExtra("stage");
        if (stage == null) {
            stage = "normal";
        }
        // 🔥 LOAD ROOMS FROM API
        loadRooms();

        // 📅 DATE PICKER
        editDate.setOnClickListener(v -> showDatePicker());

        // 🔘 BUTTON
        btnReserve.setOnClickListener(v -> reserveRoom());
    }

    // ===============================
    // LOAD ROOMS FROM API
    // ===============================
    private void loadRooms() {

        new Thread(() -> {
            try {


                String response = ApiService.getRoomsByStage(stage);
                System.out.println("ROOMS RESPONSE: " + response);

                Room[] rooms = new Gson().fromJson(response, Room[].class);

                runOnUiThread(() -> {

                    roomObjects.clear();

                    if (rooms != null) {
                        for (Room room : rooms) {
                            roomObjects.add(room);
                        }
                    }

                    RoomAdapter adapter = new RoomAdapter(this, roomObjects);
                    gridRooms.setAdapter(adapter);

                    // 🔥 CLICK ROOM
                    gridRooms.setOnItemClickListener((parent, view, position, id) -> {

                        selectedRoom = roomObjects.get(position);

                        Toast.makeText(this,
                                "Selected: " + selectedRoom.getName(),
                                Toast.LENGTH_SHORT).show();
                    });

                });

            } catch (Exception e) {
                e.printStackTrace();

                runOnUiThread(() ->
                        Toast.makeText(this, "Failed to load rooms ❌", Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }

    // ===============================
    // DATE PICKER
    // ===============================
    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, day) ->
                        editDate.setText(day + "/" + (month + 1) + "/" + year),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    // ===============================
    // RESERVE ROOM
    // ===============================
    private void reserveRoom() {

        String patient = editPatientName.getText().toString().trim();
        String date = editDate.getText().toString().trim();

        if (selectedRoom == null) {
            Toast.makeText(this, "Select a room first", Toast.LENGTH_SHORT).show();
            return;
        }

        if (patient.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String room = selectedRoom.getName();

        new Thread(() -> {
            try {

                String json = "{"
                        + "\"patientName\":\"" + patient + "\","
                        + "\"room\":\"" + room + "\","
                        + "\"date\":\"" + date + "\""
                        + "}";

                System.out.println("SEND JSON: " + json);

                ApiService.createRoomReservation(json);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Reservation Saved ✅", Toast.LENGTH_LONG).show();

                    // clear fields
                    editPatientName.setText("");
                    editDate.setText("");

                    // 🔥 reload rooms (update colors)
                    loadRooms();
                });

            } catch (Exception e) {

                e.printStackTrace();

                runOnUiThread(() ->
                        Toast.makeText(this, "Error ❌", Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }
}