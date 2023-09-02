package tn.esprit.medlist.Controllers.Utiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tn.esprit.medlist.Core.Models.Patient;
import tn.esprit.medlist.Core.Services.Implimentation.JDBCPatientService;
import tn.esprit.medlist.Core.Services.PatientService;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientForm  {


    @FXML
    private TextField patientIdField;
    @FXML
    private TextField nameField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField contactNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private TextArea symptomsArea;

    @FXML
    private Button addButton;

    @FXML
    private Label errorLabel;

    PatientService patientService = new JDBCPatientService();


    @FXML
    void savePatient(ActionEvent event) {
        try {
            // Retrieve data from form fields
            Integer patientId = Integer.valueOf(patientIdField.getText());
            String name = nameField.getText();
            String prenom = prenomField.getText();
            Integer contactNumber = Integer.valueOf(contactNumberField.getText());
            String email = emailField.getText();
            String symptoms = symptomsArea.getText();

            // Check for empty fields
            if (name.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
                errorLabel.setText("Erreur : Veuillez remplir tous les champs obligatoires.");
                return; // Stop processing if any required fields are empty
            }

            // Save the patient
            boolean saved = patientService.addPatient(new Patient(patientId, name, prenom, email, contactNumber, symptoms));

            // Check if the patient was saved successfully
            if (saved) {
                errorLabel.setText("Patient ajouté avec succès.");
            } else {
                errorLabel.setText("Erreur : Impossible d'ajouter le patient.");
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Erreur : Numéro de patient ou Identifiant du patient invalide .");
        }
    }


    @FXML
    public void initialize() {

        // Initialize your form, e.g., set up event handlers and initial states

        // Set an initial message for the error label (if needed)
        errorLabel.setText("");

        // Clear the error label when any text field is clicked
        patientIdField.setOnMouseClicked(event -> clearErrorLabel());
        nameField.setOnMouseClicked(event -> clearErrorLabel());
        prenomField.setOnMouseClicked(event -> clearErrorLabel());
        contactNumberField.setOnMouseClicked(event -> clearErrorLabel());
        emailField.setOnMouseClicked(event -> clearErrorLabel());

        // Handle the "Save" button click event
        addButton.setOnAction(this::savePatient);

    }

    private void clearErrorLabel() {
        // Clear the error label text
        errorLabel.setText("");
    }
}

