module tn.esprit.medlist {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires json;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires json.simple;
    requires com.google.gson;
    requires jdk.jsobject;
    requires com.fasterxml.jackson.databind;


    opens tn.esprit.medlist to javafx.fxml;
    exports tn.esprit.medlist;
    exports tn.esprit.medlist.Utils;
    opens tn.esprit.medlist.Utils to javafx.fxml;
    exports tn.esprit.medlist.FindLocation;
    opens tn.esprit.medlist.FindLocation to javafx.fxml;
    exports tn.esprit.medlist.LoginUser;
    opens tn.esprit.medlist.LoginUser to javafx.fxml;
    exports tn.esprit.medlist.DataBaseConnection;
    opens tn.esprit.medlist.DataBaseConnection to javafx.fxml;
    exports tn.esprit.medlist.Contact;
    opens tn.esprit.medlist.Contact to javafx.fxml;
    exports tn.esprit.medlist.FindLocation.BingAPI;
    opens tn.esprit.medlist.FindLocation.BingAPI to javafx.fxml;
}