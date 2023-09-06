package tn.esprit.medlist.Core.Models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Doctor {

    private int id;
    private String name;
    private String specialty;

    // Define a JavaFX property for available slots
    private final ObjectProperty<ObservableList<Slot>> availableSlotsProperty = new SimpleObjectProperty<>(this, "availableSlots", FXCollections.observableArrayList());

    public Doctor(int id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
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

    public ObservableList<Slot> getAvailableSlotsProperty() {
        return availableSlotsProperty.get();
    }

    public ObjectProperty<ObservableList<Slot>> availableSlotsPropertyProperty() {
        return availableSlotsProperty;
    }

    public void setAvailableSlotsProperty(ObservableList<Slot> availableSlotsProperty) {
        this.availableSlotsProperty.set(availableSlotsProperty);
    }

    public ObjectProperty<ObservableList<Slot>> availableSlotsProperty() {
        return availableSlotsProperty;
    }

    public final ObservableList<Slot> getAvailableSlots() {
        return availableSlotsProperty.get();
    }

    public final void setAvailableSlots(ObservableList<Slot> availableSlots) {
        availableSlotsProperty.set(availableSlots);
    }
}
