package tn.esprit.medlist.Core.Models;

import java.util.Date;

public class Appointment {
    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private Slot slot;

    public Appointment(int appointmentId, Patient patient, Doctor doctor, Slot slot) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.slot = slot;
        slot.bookSlot();
    }


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }
}
