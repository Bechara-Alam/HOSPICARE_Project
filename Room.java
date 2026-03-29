package com.example.tatwa10;

public class Room {

    private int id;
    private String name;
    private boolean isReserved;
    private String patientName;
    private String date;

    public String getName() { return name; }
    public boolean isReserved() { return isReserved; }
    public String getPatientName() { return patientName; }
    public String getDate() { return date; }
}