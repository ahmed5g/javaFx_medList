package tn.esprit.medlist.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import tn.esprit.medlist.Controllers.Utiles.PatientForm;
import tn.esprit.medlist.Controllers.Utiles.SceneController;
import tn.esprit.medlist.Core.Infrastructure.DataBaseConnection;
import tn.esprit.medlist.Core.Models.Doctor;
import tn.esprit.medlist.Core.Models.Patient;
import tn.esprit.medlist.Core.Models.Slot;
import tn.esprit.medlist.Core.Services.Implimentation.AppoinmentServiceImpl;
import tn.esprit.medlist.Core.Services.AppoinmentService;
import tn.esprit.medlist.Core.Services.Implimentation.JDBCPatientService;
import tn.esprit.medlist.Core.Services.Implimentation.JDBCSlotService;
import tn.esprit.medlist.Core.Services.PatientService;
import tn.esprit.medlist.Core.Services.SlotService;
import tn.esprit.medlist.Core.Utils.alertMessage;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.io.*;

public class AppointmentController extends SceneController implements Initializable {

    @FXML private PatientForm patientForm;
    @FXML private DatePicker datePicker;
    @FXML private Label slotDetailsLabel;
    @FXML private TilePane SlotSelector;
    @FXML private Button displayPatientButton;

    @FXML private Button findDorctorButton;




    //PATIENT TABLE VIEW CONFIGURATION
    @FXML private TableView<Patient> patientTableView;
    @FXML private TableColumn<Patient, Integer> idColumn;
    @FXML private TableColumn<Patient, String> nomColumn;
    @FXML private TableColumn<Patient, String> prenomColumn;
    @FXML private TableColumn<Patient, String> emailColumn;
    @FXML private TableColumn<Patient, Integer> contactNumberColumn;
    @FXML private TableColumn<Patient, String> symptomsColumn;
    @FXML private Button addPatientButton;



    //dependencies injected
    private alertMessage alertMessage;
    // Create instances using dependency injection
    SlotService slotService = new JDBCSlotService();
    PatientService patientService = new JDBCPatientService();










    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        patientForm = new PatientForm();
        //SlotSectionSelectByOne();
        SlotsAvaiblitiyAndSelection();



        //PATIENT TABLE VIEW
        // Initialize the TableView columns and associate them with Patient properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        contactNumberColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        symptomsColumn.setCellValueFactory(new PropertyValueFactory<>("Symptoms"));

        // Retrieve all patients from the PatientService
        List<Patient> patients = patientService.getAllPatients();

        // Populate the TableView with the patient data
        patientTableView.getItems().addAll(patients);

        // Add a listener to the TableView selection model
        patientTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Patient>() {
            @Override
            public void changed(ObservableValue<? extends Patient> observable, Patient oldValue, Patient newValue) {
                if (newValue != null) {
                    // A new patient is selected, fetch the patient's details by ID
                    int patientId = newValue.getId(); // Assuming you can get the patient ID
                    Patient selectedPatient = patientService.getPatientById(patientId);

                    // Update the button text with the selected patient's details
                    displayPatientButton.setText("Patient Selectionné : "
                            + "\n"
                            + "ID:" + selectedPatient.getId()
                            + "\n"
                            + "Nom:" + selectedPatient.getNom()
                            + "\n"
                            + "Prenom:" + selectedPatient.getPrenom()
                            + "\n"
                            + "Symptoms:" + selectedPatient.getSymptoms());
                } else {
                    // No patient selected, clear the button text
                    displayPatientButton.setText("Display Selected Patient");
                }
            }
        });
    }







    @FXML
    void PatientFormHandler(){
        if (patientForm.isValid()) {
            String patientName = patientForm.getPatientName();
            int age = patientForm.getAge();
            String address = patientForm.getAddress();

            // Logic to add the appointment with the given data
            // For example, create an Appointment object and add it to a list
        } else {
            alertMessage.warningMessage("veuilez verifier/completer le formulaire du patient");
        }
    }


    //TILEPANE SELECTOR FOR SLOTS AVAIBLITIY AND SELECTION
    private void SlotsAvaiblitiyAndSelection(){
        // Assuming slots is a list of available slots for a doctor
        for (Slot slot : slotService.getAllSlots()) {
            Button slotButton = new Button(slot.getStartTime() + " - " + slot.getEndTime());
            // Customize button appearance (e.g., style, size, etc.) if needed
            slotButton.setOnAction(event -> {
                // Handle slot button click event here
                // You can open a dialog or perform some action when a slot is selected
            });
            SlotSelector.getChildren().add(slotButton);
        }

    }




    //TESTING SLOT SHOW IN LABEL
    private void SlotSectionSelectByOne() {


        //TESTING WITH EXAMPLE
        Slot newSlot = slotService.createSlot(2, "2:00 PM", "4:00 PM");
        slotService.saveSlot(newSlot);

        //Show ALL Slots
        slotService.getAllSlots();
        Slot slotSelected = slotService.findSlotById(newSlot.getSlotId());


        if (slotSelected != null) {
            // Update the label with slot details
            String slotInfo = "Slot ID: " + slotSelected.getSlotId() +
                    "\nStart Time: " + slotSelected.getStartTime() +
                    "\nEnd Time: " + slotSelected.getEndTime() +
                    "\nAvailable: " + (slotSelected.isAvailable() ? "Yes" : "No");
            slotDetailsLabel.setText(slotInfo);
        } else {
            slotDetailsLabel.setText("Slot not found");
        }

    }




    @FXML
    void addPatientButtonOnClick(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) addPatientButton.getScene().getWindow();
        switchScene("AddPatient.fxml", currentStage);

    }


    @FXML
    void findDoctorButtonOnClick(ActionEvent event) throws IOException{

        Stage currentStage = (Stage) addPatientButton.getScene().getWindow();
        switchScene("FindLocation.fxml", currentStage);
    }
}


