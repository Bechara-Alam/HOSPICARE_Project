package com.example.tatwa10;

public class LoginResponse {

    private boolean success;
    private String staffId;
    private String role;
    private String name;
    public boolean isSuccess() {
        return success;
    }

    public String getStaffId() {
        return staffId;
    }
    private int doctorId;

    public int getDoctorId() {
        return doctorId;
    }
    public String getRole() {
        return role;
    }
    public String getName() {
        return name; // ✅ ADD THIS
    }
}