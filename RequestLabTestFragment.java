package com.example.tatwa10.FragmentDoctors;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tatwa10.R;
import com.example.tatwa10.ModelClass.LabResultModel;
import com.example.tatwa10.Utils.FakeLabDatabase;

public class RequestLabTestFragment extends Fragment {

    private static final int PICK_PDF = 1;

    private EditText editPatientId, editTestName, editReport;
    private Button buttonUploadPdf, buttonSend;

    private String selectedPdfName = "No PDF Selected";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_lab_test, container, false);

        editPatientId = view.findViewById(R.id.edit_patient_id);
        editTestName = view.findViewById(R.id.edit_test_name);
        editReport = view.findViewById(R.id.edit_report);

        buttonUploadPdf = view.findViewById(R.id.button_upload_pdf);
        buttonSend = view.findViewById(R.id.button_send_result);

        buttonUploadPdf.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, PICK_PDF);
        });

        buttonSend.setOnClickListener(v -> sendResult());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PDF && resultCode == getActivity().RESULT_OK && data != null) {
            Uri uri = data.getData();
            selectedPdfName = uri.getLastPathSegment();
            Toast.makeText(getContext(), "PDF Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendResult() {

        String patientId = editPatientId.getText().toString().trim();
        String testName = editTestName.getText().toString().trim();
        String report = editReport.getText().toString().trim();

        if (TextUtils.isEmpty(patientId) ||
                TextUtils.isEmpty(testName) ||
                TextUtils.isEmpty(report)) {

            Toast.makeText(getContext(), "All fields required!", Toast.LENGTH_SHORT).show();
            return;
        }

        LabResultModel result = new LabResultModel(
                patientId,
                testName,
                report,
                selectedPdfName
        );

        FakeLabDatabase.labResults.add(result);

        new AlertDialog.Builder(getContext())
                .setTitle("Success")
                .setMessage("Lab Result Sent to Patient")
                .setPositiveButton("OK", null)
                .show();

        editPatientId.setText("");
        editTestName.setText("");
        editReport.setText("");
    }
}