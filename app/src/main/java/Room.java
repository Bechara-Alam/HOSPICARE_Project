package com.example.tatwa10;

import com.google.gson.annotations.SerializedName;

public class Room {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("isReserved")
    private boolean isReserved;

    @SerializedName("patientName")
    private String patientName;

    @SerializedName("reservationDate")
    private String reservationDate;

    @SerializedName("type")
    private String type;

    @SerializedName("paymentStatus")
    private String paymentStatus;

    @SerializedName("phone")
    private String phone;

    @SerializedName("bloodType")
    private String bloodType;

    @SerializedName("address")
    private String address;

    // 🔥 FIXED HERE
    @SerializedName("startDateTime")
    private String startDateTime;

    @SerializedName("endDateTime")
    private String endDateTime;

    // ✅ GETTERS
    public int getId() { return id; }
    public String getName() { return name; }
    public boolean isReserved() { return isReserved; }
    public String getPatientName() { return patientName; }
    public String getReservationDate() { return reservationDate; }
    public String getType() { return type; }
    public String getPaymentStatus() { return paymentStatus; }
    public String getStartDateTime() { return startDateTime; }
    public String getEndDateTime() { return endDateTime; }
    public String getPhone() { return phone; }
    public String getBloodType() { return bloodType; }
    public String getAddress() { return address; }
}