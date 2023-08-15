package tn.esprit.medlist.FindLocation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import tn.esprit.medlist.FindLocation.BingAPI.BingMapsApi;
import tn.esprit.medlist.FindLocation.Models.Doctor.Doctor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private WebView mapView;
    private WebEngine webEngine;

    private DoubleProperty mapLatitude = new SimpleDoubleProperty();
    private DoubleProperty mapLongitude = new SimpleDoubleProperty();
    private StringProperty query = new SimpleStringProperty();

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

        mapView = new WebView();
        WebEngine webEngine = mapView.getEngine();

        webEngine.load(getClass().getResource("/API/BingMap/BingMap.html").toExternalForm());

        MapPane.getChildren().add(mapView);

        // Enable JavaScript and obtain the JavaScript window object
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");

                // Create a Java-to-JavaScript bridge object
                JavaScriptBridge bridge = new JavaScriptBridge();
                window.setMember("javaBridge", bridge);
            }
        });
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



