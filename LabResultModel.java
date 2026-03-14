package com.example.tatwa10.ModelClass;

public class LabResultModel {

    public String patientId;
    public String testName;
    public String report;
    public String pdfName; // only name (fake PDF)

    public LabResultModel(String patientId,
                          String testName,
                          String report,
                          String pdfName) {

        this.patientId = patientId;
        this.testName = testName;
        this.report = report;
        this.pdfName = pdfName;
    }
}