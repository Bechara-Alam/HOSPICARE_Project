package com.example.tatwa10.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

import com.example.tatwa10.ApiService;
import com.example.tatwa10.R;
import com.example.tatwa10.ReservationActivity;
import com.example.tatwa10.Room;

import java.util.List;

public class RoomAdapter extends BaseAdapter {

    private Context context;
    private List<Room> rooms;

    public RoomAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rooms.get(position).getId();
    }



    private int selectedPosition = -1; // 🔥 ADD THIS AT TOP OF CLASS

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        }

        TextView name = convertView.findViewById(R.id.text_room_name);
        TextView status = convertView.findViewById(R.id.text_status);
        TextView patient = convertView.findViewById(R.id.text_patient);
        Button btnCancel = convertView.findViewById(R.id.btn_cancel);

        Room room = rooms.get(position);
        TextView time = convertView.findViewById(R.id.text_time);

        String start = room.getStartDateTime() != null ? room.getStartDateTime() : "N/A";
        String end = room.getEndDateTime() != null ? room.getEndDateTime() : "N/A";

        time.setText("From: " + start + "\nTo: " + end);
        // ===============================
        // 🔥 CLICK → SELECT ROOM
        // ===============================
        convertView.setOnClickListener(v -> {

            if (room.isReserved()) {
                Toast.makeText(context, "Room already reserved ❌", Toast.LENGTH_SHORT).show();
                return;
            }

            selectedPosition = position;

            if (context instanceof ReservationActivity) {
                ((ReservationActivity) context).onRoomSelected(room);
            }

            notifyDataSetChanged();
        });

        // ===============================
        // 🔥 LONG CLICK → HISTORY
        // ===============================
        convertView.setOnLongClickListener(v -> {

            new Thread(() -> {
                try {

                    String response = ApiService.getRoomHistory(room.getId());

                    if (response == null || response.isEmpty()) {
                        ((Activity) context).runOnUiThread(() ->
                                Toast.makeText(context, "No history found", Toast.LENGTH_SHORT).show()
                        );
                        return;
                    }

                    org.json.JSONArray array = new org.json.JSONArray(response);

                    if (array.length() == 0) {
                        ((Activity) context).runOnUiThread(() ->
                                Toast.makeText(context, "No history for this room", Toast.LENGTH_SHORT).show()
                        );
                        return;
                    }

                    StringBuilder formatted = new StringBuilder();

                    for (int i = 0; i < array.length(); i++) {

                        org.json.JSONObject obj = array.getJSONObject(i);

                        formatted.append("👤 Patient: ")
                                .append(obj.optString("patientName", "N/A")).append("\n");

                        formatted.append("📅 Date: ")
                                .append(obj.optString("reservationDate", "N/A")).append("\n");

                        formatted.append("⏱ From: ")
                                .append(obj.optString("startDateTime", "Not set")).append("\n");

                        formatted.append("⏱ To: ")
                                .append(obj.optString("endDateTime", "Not finished")).append("\n");

                        formatted.append("💳 Payment: ")
                                .append(obj.optString("paymentStatus", "N/A")).append("\n");

                        formatted.append("📞 Phone: ")
                                .append(obj.optString("phone", "N/A")).append("\n");

                        formatted.append("🩸 Blood: ")
                                .append(obj.optString("bloodType", "N/A")).append("\n");

                        formatted.append("📍 Address: ")
                                .append(obj.optString("address", "N/A")).append("\n");

                        formatted.append("\n-------------------------\n\n");
                    }

                    ((Activity) context).runOnUiThread(() -> {

                        new AlertDialog.Builder(context)
                                .setTitle("Room History 🏥")
                                .setMessage(formatted.toString())
                                .setPositiveButton("OK", null)
                                .show();

                    });

                } catch (Exception e) {
                    e.printStackTrace();

                    ((Activity) context).runOnUiThread(() ->
                            Toast.makeText(context, "Error loading history ❌", Toast.LENGTH_SHORT).show()
                    );
                }
            }).start();

            return true;
        });

        // ===============================
        // ROOM NAME
        // ===============================
        name.setText(room.getName());

        // ===============================
        // STATUS + PATIENT INFO
        // ===============================
        if (room.isReserved()) {

            status.setText("Reserved");
            status.setTextColor(Color.RED);

            String info = "";

            if (room.getPatientName() != null)
                info += "Patient: " + room.getPatientName();

            if (room.getReservationDate() != null)
                info += "\nDate: " + room.getReservationDate();

            if (room.getPaymentStatus() != null)
                info += "\nPayment: " + room.getPaymentStatus();

            if (room.getPhone() != null)
                info += "\nPhone: " + room.getPhone();

            if (room.getBloodType() != null)
                info += "\nBlood: " + room.getBloodType();

            if (room.getAddress() != null)
                info += "\nAddress: " + room.getAddress();

            patient.setText(info);

            btnCancel.setVisibility(View.VISIBLE);

            btnCancel.setOnClickListener(v -> {

                new AlertDialog.Builder(context)
                        .setTitle("Cancel Reservation")
                        .setMessage("Are you sure you want to cancel this reservation?")
                        .setPositiveButton("Yes", (dialog, which) -> {

                            new Thread(() -> {
                                try {

                                    ApiService.cancelRoom(room.getId());

                                    ((Activity) context).runOnUiThread(() -> {
                                        Toast.makeText(context, "Reservation Cancelled ❌", Toast.LENGTH_SHORT).show();

                                        if (context instanceof ReservationActivity) {
                                            ((ReservationActivity) context).loadRooms();
                                        }
                                    });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }).start();

                        })
                        .setNegativeButton("No", null)
                        .show();
            });

        } else {

            status.setText("Available");
            status.setTextColor(Color.parseColor("#2E7D32"));
            patient.setText("");
            btnCancel.setVisibility(View.INVISIBLE);
        }

        // ===============================
        // ROOM TYPE COLORS
        // ===============================
        if (room.getType() != null) {

            switch (room.getType().toLowerCase()) {

                case "icu":
                    convertView.setBackgroundColor(Color.parseColor("#FFCDD2"));
                    break;

                case "vip":
                    convertView.setBackgroundColor(Color.parseColor("#FFF9C4"));
                    break;

                case "maternity":
                    convertView.setBackgroundColor(Color.parseColor("#E1BEE7"));
                    break;

                default:
                    convertView.setBackgroundColor(Color.parseColor("#C8E6C9"));
                    break;
            }

        } else {
            convertView.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }

        // ===============================
        // 🔥 SELECTED HIGHLIGHT (LAST!)
        // ===============================
        if (position == selectedPosition) {
            convertView.setBackgroundColor(Color.parseColor("#BBDEFB"));
        }

        return convertView;
    }
}