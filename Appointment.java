package com.example.tatwa10.ModelClass;

public class Appointment {

    private String id;
    private String doctorName;
    private String appointmentDate;
    private String appointmentTime;
    private boolean appointmentDone;
    private boolean appointmentAccepted;
    private String name;
    private String paymentMethod;

    public Appointment(String id,
                       String doctorName,
                       String appointmentDate,
                       String appointmentTime,
                       boolean appointmentDone,
                       boolean appointmentAccepted,
                       String name,
                       String paymentMethod) {

        this.id = id;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentDone = appointmentDone;
        this.appointmentAccepted = appointmentAccepted;
        this.name = name;
        this.paymentMethod = paymentMethod;
    }

    public Appointment() {}

    // GETTERS

    public String getId() { return id; }
    public String getDoctorName() { return doctorName; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getAppointmentTime() { return appointmentTime; }
    public boolean isAppointmentDone() { return appointmentDone; }
    public boolean isAppointmentAccepted() { return appointmentAccepted; }
    public String getName() { return name; }
    public String getPaymentMethod() { return paymentMethod; }

    // SETTERS

    public void setAppointmentAccepted(boolean appointmentAccepted) {
        this.appointmentAccepted = appointmentAccepted;
    }

    public void setAppointmentDone(boolean appointmentDone) {
        this.appointmentDone = appointmentDone;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}