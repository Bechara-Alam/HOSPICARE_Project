package com.example.tatwa10.Models;

public class LabTest {
    private int id;
    private String name;
    private String result;
    private String date;
    private String patientId;  // to filter per patient

    public LabTest(int id, String name, String result, String date, String patientId) {
        this.id = id;
        this.name = name;
        this.result = result;
        this.date = date;
        this.patientId = patientId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getResult() { return result; }
    public String getDate() { return date; }
    public String getPatientId() { return patientId; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setResult(String result) { this.result = result; }
    public void setDate(String date) { this.date = date; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
}