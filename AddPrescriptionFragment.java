package com.example.tatwa10.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tatwa10.MainActivity;
import com.example.tatwa10.ModelClass.Prescription;
import com.example.tatwa10.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPrescriptionFragment extends Fragment {

    private Spinner spinnerNames;
    private NumberPicker numberPickerDuration;
    private EditText editTextMedicineName;
    private CheckBox checkBoxBreakfast;
    private CheckBox checkBoxLunch;
    private CheckBox checkBoxDinner;
    private ImageView imageViewDateStart;
    private TextView textViewDateStart;
    private TextView textViewDateEnd;
    private Calendar calendarDateStart;
    private Calendar calendarDateEnd;

    // 🔹 Fake phone number (no Firebase)
    private String authNumber = "+0000000000";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_prescription, container, false);
        MainActivity.currentFragment = "addPrescription";
        setHasOptionsMenu(true);

        spinnerNames = view.findViewById(R.id.spinner_doctors_list);
        String[] names = getContext().getResources().getStringArray(R.array.doctors_name);

        ArrayAdapter<String> adapterNames = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                names
        );
        adapterNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNames.setAdapter(adapterNames);

        numberPickerDuration = view.findViewById(R.id.number_picker_duration);
        numberPickerDuration.setMinValue(1);
        numberPickerDuration.setMaxValue(50);

        editTextMedicineName = view.findViewById(R.id.edit_text_medicine_name);
        checkBoxBreakfast = view.findViewById(R.id.checkbox_breakfast);
        checkBoxLunch = view.findViewById(R.id.checkbox_lunch);
        checkBoxDinner = view.findViewById(R.id.checkbox_dinner);

        imageViewDateStart = view.findViewById(R.id.image_view_date_start);
        textViewDateStart = view.findViewById(R.id.text_view_add_date_start);
        textViewDateEnd = view.findViewById(R.id.text_view_add_date_end);

        DateFormat dateFormat = new SimpleDateFormat("E ,dd MMM yyyy");
        calendarDateStart = Calendar.getInstance();
        calendarDateEnd = Calendar.getInstance();

        textViewDateStart.setText(dateFormat.format(calendarDateStart.getTime()));

        imageViewDateStart.setOnClickListener(v -> showDatePicker());

        numberPickerDuration.setOnValueChangedListener((picker, oldVal, newVal) -> {
            calendarDateEnd = (Calendar) calendarDateStart.clone();
            calendarDateEnd.add(Calendar.DATE, newVal - 1);

            DateFormat df = new SimpleDateFormat("E ,dd MMM yyyy");
            textViewDateEnd.setText(df.format(calendarDateEnd.getTime()));
        });

        return view;
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();

        Calendar cal = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", cal.get(Calendar.YEAR));
        args.putInt("month", cal.get(Calendar.MONTH));
        args.putInt("day", cal.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        DatePickerDialog.OnDateSetListener onDate = (view, year, month, day) -> {

            calendarDateStart.set(Calendar.YEAR, year);
            calendarDateStart.set(Calendar.MONTH, month);
            calendarDateStart.set(Calendar.DATE, day);

            DateFormat dfStart = new SimpleDateFormat("E ,dd MMM yyyy");
            textViewDateStart.setText(dfStart.format(calendarDateStart.getTime()));

            calendarDateEnd = (Calendar) calendarDateStart.clone();
            calendarDateEnd.add(Calendar.DATE, numberPickerDuration.getValue() - 1);

            DateFormat dfEnd = new SimpleDateFormat("E ,dd MMM yyyy");
            textViewDateEnd.setText(dfEnd.format(calendarDateEnd.getTime()));
        };

        date.setCallBack(onDate);
        date.show(getParentFragmentManager(), "Date Picker");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_prescription_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.icon_save_prescription) {
            savePrescription();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePrescription() {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String doctorName = spinnerNames.getSelectedItem().toString();
        String medicineName = editTextMedicineName.getText().toString().trim();
        boolean breakfast = checkBoxBreakfast.isChecked();
        boolean lunch = checkBoxLunch.isChecked();
        boolean dinner = checkBoxDinner.isChecked();
        int duration = numberPickerDuration.getValue();
        String dateStart = textViewDateStart.getText().toString();
        String dateEnd = textViewDateEnd.getText().toString();

        if (TextUtils.isEmpty(medicineName)) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Please add medicine", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!breakfast && !lunch && !dinner) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Please select time", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔹 FRONT-END DEMO ONLY (no Firebase)
        Prescription prescription = new Prescription(
                authNumber,
                doctorName,
                medicineName,
                breakfast,
                lunch,
                dinner,
                dateStart,
                dateEnd,
                duration,
                MainActivity.patientName
        );

        progressDialog.dismiss();
        Toast.makeText(getContext(), "Prescription Added (Demo)", Toast.LENGTH_SHORT).show();

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new PrescriptionFragment())
                .commit();
    }
}
