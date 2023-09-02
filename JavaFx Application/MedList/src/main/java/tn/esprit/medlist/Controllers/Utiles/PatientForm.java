package tn.esprit.medlist.Controllers.Utiles;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PatientForm extends VBox {

    private TextField patientNameField = new TextField();
    private TextField ageField = new TextField();
    private TextField addressField = new TextField();

    public PatientForm() {
        initializeUI();
    }

    private void initializeUI() {
        setSpacing(10);
        getChildren().addAll(
                new Label("Patient Name"), patientNameField,
                new Label("Age"), ageField,
                new Label("Address"), addressField
        );
    }

    public String getPatientName() {
        return patientNameField.getText();
    }

    public int getAge() {
        return Integer.parseInt(ageField.getText());
    }

    public String getAddress() {
        return addressField.getText();
    }

    public boolean isValid() {
        return !patientNameField.getText().isEmpty() &&
                !ageField.getText().isEmpty() &&
                isInteger(ageField.getText()); // Validate age as integer
        // Add more validation checks as needed
    }

    private boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void savePatient(ActionEvent actionEvent) {
    }

    public void returnToAppoinment(ActionEvent actionEvent) {
    }
}

