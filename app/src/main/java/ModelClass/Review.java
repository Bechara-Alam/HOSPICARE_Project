package com.example.tatwa10.ModelClass;

public class Review {

    private int id;            // 🔥 ADD THIS
    private int doctorId;
    private String patientName;
    private float rating;
    private String comment;
    private String createdAt;

    public int getId() {
        return id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getPatientName() {
        return patientName;
    }

    public float getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}