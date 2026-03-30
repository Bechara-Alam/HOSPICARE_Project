package com.example.tatwa10.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

import com.example.tatwa10.R;
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
    public int getCount() { return rooms.size(); }

    @Override
    public Object getItem(int position) { return rooms.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Room room = rooms.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        }

        TextView txtRoom = convertView.findViewById(R.id.txt_room);

        if (room.isReserved()) {
            txtRoom.setBackgroundColor(Color.RED);
            txtRoom.setText(
                    room.getName() + "\n" +
                            room.getPatientName() + "\n" +
                            room.getDate()
            );
        } else {
            txtRoom.setBackgroundColor(Color.GREEN);
            txtRoom.setText(room.getName());
        }

        return convertView;
    }
}