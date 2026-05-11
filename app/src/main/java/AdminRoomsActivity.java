package com.example.tatwa10;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tatwa10.Adapters.RoomAdapter;
import com.google.gson.Gson;

import java.util.*;

public class AdminRoomsActivity extends AppCompatActivity {

    private ListView listRooms;
    private List<Room> rooms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rooms);

        listRooms = findViewById(R.id.list_rooms);

        loadRooms();
    }

    private void loadRooms() {

        new Thread(() -> {
            try {

                String response = ApiService.getRoomsByStage("normal"); // or stage variable
                Room[] data = new Gson().fromJson(response, Room[].class);

                runOnUiThread(() -> {

                    rooms.clear();
                    Collections.addAll(rooms, data);

                    RoomAdapter adapter = new RoomAdapter(this, rooms);
                    listRooms.setAdapter(adapter);
                    listRooms.setOnItemLongClickListener((parent, view, position, id) -> {

                        Room room = rooms.get(position);

                        if (room.isReserved()) {

                            new Thread(() -> {
                                ApiService.cancelRoom(room.getId());

                                runOnUiThread(() -> {
                                    Toast.makeText(this, "Reservation Cancelled ❌", Toast.LENGTH_SHORT).show();
                                    loadRooms(); // 🔥 refresh list
                                });

                            }).start();

                        } else {
                            Toast.makeText(this, "Room is already available", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    });
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}