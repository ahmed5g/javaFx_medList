package tn.esprit.medlist.Controllers.Utiles;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import tn.esprit.medlist.Core.Models.LocationInfo;

public class MapController {
    @FXML private WebView mapWebView;

    WebEngine webEngine = mapWebView.getEngine();
    public void initialize() {

        webEngine.load(getClass().getResource("/API/BingMap/BingMap.html").toExternalForm());

        // Access the JavaScript window object
        JSObject window = (JSObject) webEngine.executeScript("window");

        // Pass data from Java to JavaScript
        window.setMember("javaApp", this); // "javaApp" is a JavaScript variable
    }

    // Method to add data to the layer from Java
    public void addDataToLayer(String data) {

        webEngine.executeScript("addDataToLayer(" + data + ");");
    }


    // Method to display InfoBoxes in JavaScript
    public void displayInfoBox(LocationInfo locationInfo) {

        webEngine.executeScript("displayInfoBox(" + locationInfoToJson(locationInfo) + ");");
    }

    // Helper method to convert LocationInfo to JSON string
    private String locationInfoToJson(LocationInfo locationInfo) {
        // Implement JSON serialization logic here
        // You can use libraries like Gson to convert the object to JSON
        // For simplicity, let's assume a basic JSON format:
        return "{\"displayName\":\"" + locationInfo.getDisplayName() + "\", " +
                "\"addressLine\":\"" + locationInfo.getAddressLine() + "\", " +
                "\"locality\":\"" + locationInfo.getLocality() + "\", " +
                "\"latitude\":" + locationInfo.getLatitude() + ", " +
                "\"longitude\":" + locationInfo.getLongitude() + "}";
    }
}
