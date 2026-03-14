package com.example.tatwa10.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.tatwa10.R;

public class BookLabTestFragment extends Fragment {

    private Spinner spinnerTests;
    private EditText editDate;
    private EditText editNotes;
    private Button buttonSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_lab_test, container, false);

        spinnerTests = view.findViewById(R.id.spinner_tests);
        editDate = view.findViewById(R.id.edit_date);
        editNotes = view.findViewById(R.id.edit_notes);
        buttonSubmit = view.findViewById(R.id.button_submit_lab);

        String[] tests = {
                "Complete Blood Count (CBC)",
                "Blood Sugar Test",
                "X-Ray",
                "MRI Scan",
                "COVID-19 Test"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        tests);

        spinnerTests.setAdapter(adapter);

        buttonSubmit.setOnClickListener(v -> submitBooking());

        return view;
    }

    private void submitBooking() {

        String date = editDate.getText().toString().trim();

        if (TextUtils.isEmpty(date)) {
            editDate.setError("Please select a date");
            return;
        }

        Toast.makeText(getContext(),
                "Lab Test Booked Successfully!",
                Toast.LENGTH_LONG).show();
    }
}