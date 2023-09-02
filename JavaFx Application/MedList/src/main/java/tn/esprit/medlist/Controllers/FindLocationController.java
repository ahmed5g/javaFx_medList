package tn.esprit.medlist.Controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import tn.esprit.medlist.Controllers.Utiles.JavaScriptBridge;
import tn.esprit.medlist.Controllers.Utiles.MapController;
import tn.esprit.medlist.Core.Models.LocationInfo;
import tn.esprit.medlist.Core.Models.Slot;
import tn.esprit.medlist.Core.Services.Implimentation.JDBCPatientService;
import tn.esprit.medlist.Core.Services.Implimentation.JDBCSlotService;
import tn.esprit.medlist.Core.Services.PatientService;
import tn.esprit.medlist.Core.Services.SlotService;
import tn.esprit.medlist.Core.Utils.BingAPI.BingMapsApi;

import java.net.URL;
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


    @FXML private TilePane SlotSelector;
    @FXML private Label slotDetailsLabel;


    private DoubleProperty mapLatitude = new SimpleDoubleProperty();
    private DoubleProperty mapLongitude = new SimpleDoubleProperty();
    private StringProperty query = new SimpleStringProperty();


    MapController mapController ; // Get the MapController instance




    SlotService slotService = new JDBCSlotService();

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


        mapController.initialize();

        MapPane.getChildren().add(map);


        // Create a sample LocationInfo object
        LocationInfo locationInfo = new LocationInfo("Sample Location","123 Main St","Cityville"
                ,37.7749,-122.4194);


// Display the InfoBox on the map
        mapController.displayInfoBox(locationInfo);


        // Enable JavaScript and obtain the JavaScript window object
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");

                // Create a Java-to-JavaScript bridge object
                JavaScriptBridge bridge = new JavaScriptBridge();
                window.setMember("javaBridge", bridge);
            }
        });

        SlotsAvaiblitiyAndSelection();
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



