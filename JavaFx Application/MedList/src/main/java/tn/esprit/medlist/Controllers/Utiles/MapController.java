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


}
