package tn.esprit.medlist.Controllers.Utiles;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.io.IOException;
import java.net.URL;

public abstract class SceneController {

    private double x, y; // for drag movement

    public void switchScene(String fxmlFileName, Stage currentStage) throws IOException {


        URL url = new File("src/main/resources/tn/esprit/medlist/" + fxmlFileName).toURI().toURL();
        if (url == null) {
            throw new IOException("FXML file not found: " + fxmlFileName);
        }

        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        // Configuring stage for drag movement
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            currentStage.setX(event.getScreenX() - x);
            currentStage.setY(event.getScreenY() - y);
        });


        currentStage.setScene(scene);

        // Set the stage style and scene configuration before showing the stage
        currentStage.show();
    }
}
