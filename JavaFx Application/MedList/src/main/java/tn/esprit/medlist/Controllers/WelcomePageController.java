package tn.esprit.medlist.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.medlist.Controllers.Utiles.SceneController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class WelcomePageController extends SceneController implements Initializable {



    @FXML private Button appoinmentButton;


    @FXML
    void onAppoinmentButtonClicked(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) appoinmentButton.getScene().getWindow();
        switchScene("Appoinment.fxml", currentStage);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
