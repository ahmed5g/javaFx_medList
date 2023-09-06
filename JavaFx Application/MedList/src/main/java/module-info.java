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

    requires jdk.jsobject;
    requires com.fasterxml.jackson.databind;


    opens tn.esprit.medlist to javafx.fxml;
    exports tn.esprit.medlist;
    exports tn.esprit.medlist.Core.Utils;
    opens tn.esprit.medlist.Core.Utils to javafx.fxml;

    exports tn.esprit.medlist.Core.Infrastructure;
    opens tn.esprit.medlist.Core.Infrastructure to javafx.fxml;

    exports tn.esprit.medlist.Core.Utils.BingAPI;
    opens tn.esprit.medlist.Core.Utils.BingAPI to javafx.fxml;
    exports tn.esprit.medlist.Controllers;
    opens tn.esprit.medlist.Controllers to javafx.fxml;
    exports tn.esprit.medlist.Core.Models.Users;
    opens tn.esprit.medlist.Core.Models.Users to javafx.fxml;
    exports tn.esprit.medlist.Core.Models;
    opens tn.esprit.medlist.Core.Models to javafx.fxml;
    exports tn.esprit.medlist.Controllers.Utiles;
    opens tn.esprit.medlist.Controllers.Utiles to javafx.fxml;
}