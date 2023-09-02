package tn.esprit.medlist.Core.Models;

import java.util.List;

public class Doctor {

    private int id;
    private String name;

    private String specialty;
    private List<Slot> availableSlots;

    public Doctor(int id, String name, String specialty, List<Slot> availableSlots) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.availableSlots = availableSlots;
    }

    public Doctor(int id, String name, String specialty) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setAvailableSlots(List<Slot> availableSlots) {
        this.availableSlots = availableSlots;
    }

    public void addAvailableSlot(Slot slot) {
        availableSlots.add(slot);
    }

    public List<Slot> getAvailableSlots() {
        return availableSlots;
    }
}
