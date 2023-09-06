package tn.esprit.medlist.Controllers;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import tn.esprit.medlist.Controllers.Utiles.JavaScriptBridge;
import tn.esprit.medlist.Controllers.Utiles.MapController;
import tn.esprit.medlist.Core.Models.Doctor;
import tn.esprit.medlist.Core.Models.LocationInfo;
import tn.esprit.medlist.Core.Models.Patient;
import tn.esprit.medlist.Core.Models.Appointment;
import tn.esprit.medlist.Core.Models.Slot;
import tn.esprit.medlist.Core.Services.AppoinmentService;
import tn.esprit.medlist.Core.Services.DoctorService;
import tn.esprit.medlist.Core.Services.Implimentation.AppoinmentServiceImpl;
import tn.esprit.medlist.Core.Services.Implimentation.JDBCDrService;
import tn.esprit.medlist.Core.Services.Implimentation.JDBCPatientService;
import tn.esprit.medlist.Core.Services.Implimentation.JDBCSlotService;
import tn.esprit.medlist.Core.Services.PatientService;
import tn.esprit.medlist.Core.Services.SlotService;
import tn.esprit.medlist.Core.Utils.BingAPI.BingMapsApi;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class FindLocationController implements Initializable {

    @FXML
    private TextField search;


    @FXML
    private TextArea bodyQueryResponse;

    @FXML
    private WebView DisplayMapPane;
    @FXML
    private AnchorPane MapPane;
    @FXML private WebView map;
    private WebEngine webEngine;

    @FXML
    private Label AddSlotToDoctorMessage;

    @FXML
    private Label DoctorSelectedLabel;

    @FXML
    private Label DoctorSpecialitySelectedLAB;

    @FXML
    private Label SlotSelectedLAB;


    @FXML private TilePane SlotSelector;
    @FXML private Label slotDetailsLabel;
    @FXML private TableView<Doctor> doctorTableView;
    @FXML private TableColumn<Doctor, Integer> idColumn;
    @FXML private TableColumn<Doctor, String> nomColumn;
    @FXML private TableColumn<Doctor, String> SpecialityColumn;
    @FXML private TableColumn<Doctor, ObservableList<Slot>> availableColumn;




    // Define observable properties for selected doctor and slot
    private final SimpleObjectProperty<Doctor> selectedDoctor = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Slot> selectedSlot = new SimpleObjectProperty<>();

    private DoubleProperty mapLatitude = new SimpleDoubleProperty();
    private DoubleProperty mapLongitude = new SimpleDoubleProperty();
    private StringProperty query = new SimpleStringProperty();



    MapController mapController ; // Get the MapController instance




    SlotService slotService = new JDBCSlotService();
    DoctorService doctorService = new JDBCDrService();
    PatientService patientService = new JDBCPatientService();
    AppoinmentService appoinmentService = new AppoinmentServiceImpl();

    @FXML
    void search(ActionEvent event) {
        String userLocation = search.getText();

        try {

            String geocodingApiResponse = BingMapsApi.CallGeoCodingApi(userLocation);
            System.out.println("Geocoding API Response:\n" + geocodingApiResponse);

            double latitude = 0.0;
            double longitude = 0.0;

            JSONObject jsonResponse = new JSONObject(geocodingApiResponse);
            JSONArray results = jsonResponse.getJSONArray("resourceSets").getJSONObject(0).getJSONArray("resources");

            if (results.length() > 0) {
                JSONObject resource = results.getJSONObject(0);
                JSONObject point = resource.getJSONObject("point");
                JSONArray coordinates = point.getJSONArray("coordinates");
                latitude = coordinates.getDouble(0);
                longitude = coordinates.getDouble(1);
            }


            String localSearchApiResponse = BingMapsApi.CallLocalSearchApi(latitude, longitude);
            System.out.println("Local Search API Response:\n" + localSearchApiResponse);

            // Process localSearchApiResponse and display doctor info

            try {
                JSONArray doctorResults = new JSONObject(localSearchApiResponse)
                        .getJSONArray("resourceSets")
                        .getJSONObject(0)
                        .getJSONArray("resources");

                StringBuilder healthcareProfessionalInfo = new StringBuilder();

                for (int i = 0; i < doctorResults.length(); i++) {
                    JSONObject doctorResource = doctorResults.getJSONObject(i);
                    String doctorName = doctorResource.getString("name");
                    healthcareProfessionalInfo.append("Doctor Name: ").append(doctorName).append("\n");

                    String phoneNumber = doctorResource.optString("PhoneNumber", "N/A");
                    healthcareProfessionalInfo.append("Phone Number: ").append(phoneNumber).append("\n");

                    String website = doctorResource.optString("Website", "N/A");
                    healthcareProfessionalInfo.append("Website: ").append(website).append("\n");

                    JSONObject addressObject = doctorResource.getJSONObject("Address");
                    String formattedAddress = addressObject.optString("formattedAddress", "N/A");
                    healthcareProfessionalInfo.append("Address: ").append(formattedAddress).append("\n");

                    healthcareProfessionalInfo.append("\n");
                }

                System.out.println(healthcareProfessionalInfo.toString());


            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateMarkerLocation(double latitude, double longitude) {
        DisplayMapPane.getEngine().executeScript("updateMarker(" + latitude + ", " + longitude + ")");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the TableView columns and associate them with Doctor properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SpecialityColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        availableColumn.setCellValueFactory(cellData -> cellData.getValue().availableSlotsProperty());

        // Populate the TableView with doctors and their available slots
        List<Doctor> doctorsWithSlots = doctorService.getAllDoctorsWithAvailableSlots();
        doctorTableView.setItems(FXCollections.observableArrayList(doctorsWithSlots));


        doctorTableView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Doctor> observable, Doctor oldValue, Doctor newValue) -> {
                    selectedDoctor.set(newValue); // Update the selected doctor
                    if (newValue != null) {
                        // A new doctor is selected, you can access it using 'newValue'
                        Doctor selectedDoctor = newValue;
                        List<Slot> s = (List<Slot>) selectedSlot.getValue();
                        selectedDoctor.setAvailableSlots((ObservableList<Slot>) s);
                        selectedDoctor.getAvailableSlots();
                        // Update labels to display selected doctor's information
                        DoctorSelectedLabel.setText(selectedDoctor.getName());
                        DoctorSpecialitySelectedLAB.setText(selectedDoctor.getSpecialty());
                        // Now you can perform actions with the selected doctor
                        // For example, display details or enable/disable buttons based on the selected doctor
                        System.out.println("Selected Doctor: " + selectedDoctor.getName() + selectedDoctor.getSpecialty());
                    } else {
                        // Clear labels when no doctor is selected
                        DoctorSelectedLabel.setText("");
                        DoctorSpecialitySelectedLAB.setText("");
                    }
                }
        );

        // Initialize the WebView for the map
        WebView map = new WebView();
        webEngine = map.getEngine();
        webEngine.load(getClass().getResource("/API/BingMap/BingMap.html").toExternalForm());
        MapPane.getChildren().add(map);

        // Enable JavaScript and obtain the JavaScript window object
        webEngine.getLoadWorker().stateProperty().addListener(
                (ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        JSObject window = (JSObject) webEngine.executeScript("window");

                        // Create a Java-to-JavaScript bridge object
                        JavaScriptBridge bridge = new JavaScriptBridge();
                        window.setMember("javaBridge", bridge);
                    }
                }
        );

        // Initialize slot selection and display
        SlotsAvaiblitiyAndSelection();
        SlotSelectedLAB.setText("");
    }



    private void SlotsAvaiblitiyAndSelection() {
        // Assuming slots is a list of available slots for a doctor
        List<Slot> availableSlots = slotService.getAllSlots();

        for (Slot slot : availableSlots) {
            Button slotButton = new Button(slot.getStartTime() + " - " + slot.getEndTime());
            // Customize button appearance (e.g., style, size, etc.) if needed
            slotButton.setOnAction(event -> {
                // Retrieve the selected slot using slotService.findSlotById()
                int selectedSlotId = slot.getSlotId(); // Get the ID of the selected slot
                Slot selectedSlot = slotService.findSlotById(selectedSlotId);

                SlotSelectedLAB.setText("Selected Slot: " + selectedSlot.getStartTime() + " - " + selectedSlot.getEndTime());
                // Now you can use the selectedSlot as needed
                if (selectedSlot != null) {
                    // Do something with the selected slot
                    System.out.println("Selected Slot: " + selectedSlot.getStartTime() + " - " + selectedSlot.getEndTime());
                } else {
                    // Handle the case where the slot couldn't be found
                    System.out.println("Selected slot not found.");
                }
            });
            SlotSelector.getChildren().add(slotButton);

        }
    }



    // In your PatientService or a dedicated service class
    public Patient getRandomPatientById(int patientId) {
        // Implement logic to fetch a random patient by ID
        // For demonstration, you can return a static patient with the given ID
        // Replace this with your actual logic to fetch a patient
        return new Patient(patientId, "Random Patient", "Random LastName", "random@example.com", 1234567890, "Random Symptoms");
    }
    @FXML
    void addSlotToDoctorButtonOnClick(ActionEvent event) {
        // Get the selected doctor and slot from the observable properties
        //Doctor selectedDoctor = this.selectedDoctor.get();
        //Slot selectedSlot = this.selectedSlot.get();

        // Get a random patient (you can replace 1 with a random patient ID)
        Patient selectedPatient = patientService.getPatientById(1);
        Doctor SelectedDcotor = doctorService.getDoctorById(1);

        Slot SlectedSlot = slotService.findSlotById(2);

        // Check if the selected slot is available
        if (SlectedSlot.isAvailable()) {
            // Create an appointment
            Appointment appointment = appoinmentService.scheduleAppoinment(3, selectedPatient, SelectedDcotor, SlectedSlot);

            // Save the appointment
            appoinmentService.saveAppointment(appointment);

            // Update the slot availability to mark it as occupied
            SlectedSlot.setAvailable(false);

            // You may want to update the UI or database to reflect the changes

            System.out.println("Appointment ADDED");

            System.out.println(SelectedDcotor.getAvailableSlots());

            // Check if both a doctor and a slot are selected

        }
    }




    private Slot getSelectedSlot() {
        return selectedSlot.get();
    }




    // Method to initiate Bing Maps API request for general search
        public void searchQuery() {
            // Construct Bing Maps API request URL for local search
            String apiUrl = "https://dev.virtualearth.net/REST/v1/LocalSearch/?query=" +
                    encodeURIComponent("restaurants") + "&userMapView=" +
                    mapLatitude.get() + "," + mapLongitude.get() + "&maxResults=10&key=" + BingMapsApi.BING_MAPS_API_KEY;

            // Call JavaScript method to perform search and display results
            webEngine.executeScript("searchQuery('" + apiUrl + "')");
        }

        // Helper method to encode special characters for query string
        private String encodeURIComponent(String s) {
            try {
                return java.net.URLEncoder.encode(s, "UTF-8")
                        .replaceAll("\\+", "%20")
                        .replaceAll("\\%21", "!")
                        .replaceAll("\\%27", "'")
                        .replaceAll("\\%28", "(")
                        .replaceAll("\\%29", ")")
                        .replaceAll("\\%7E", "~");
            } catch (Exception e) {
                return s;
            }
        }


    }



