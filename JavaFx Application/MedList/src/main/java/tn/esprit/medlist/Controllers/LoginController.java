package tn.esprit.medlist.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.medlist.Controllers.Utiles.SceneController;
import tn.esprit.medlist.Core.Infrastructure.DataBaseConnection;
import tn.esprit.medlist.Core.Models.Users.getData;
import tn.esprit.medlist.Core.Utils.alertMessage;

import java.sql.*;

public class LoginController extends SceneController {

    @FXML
    private Button login_btn;

    @FXML
    private AnchorPane login_form;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private double x = 0;
    private double y = 0;




    //DATA BASE INITIALIZATION
    DataBaseConnection dbConnection = DataBaseConnection.getInstance();
    Connection connection = dbConnection.getConnection();


    @FXML
    public void login() {

        alertMessage alert = new alertMessage();

        // CHECK IF ALL OR SOME OF THE FIELDS ARE EMPTY
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            alert.errorMessage("Incorrect Username/Password");
        } else {
            String selectData = "SELECT * FROM users WHERE "
                    + "username = ? and password = ?"; // users IS OUR TABLE NAME






            try {
                prepare = connection.prepareStatement(selectData);
                prepare.setString(1, username.getText());
                prepare.setString(2, password.getText());

                result = prepare.executeQuery();

                if(username.getText().isEmpty() || password.getText().isEmpty()){
                    alert.errorMessage("Champs Vides Obligatoires ");
                }else {

                    if (result.next()) {
                        // ONCE ALL DATA THAT USERS INSERT ARE CORRECT, THEN WE WILL PROCEED TO OUR MAIN FORM
                        getData.username = username.getText();
                        alert.successMessage("Successfully Login!");


                        Stage currentStage = (Stage) login_btn.getScene().getWindow();
                        switchScene("WelcomePage.fxml", currentStage);

                        //@FXML
                        //    private void onLoginButtonClicked(ActionEvent event) throws IOException {
                        //        Stage currentStage = (Stage) loginButton.getScene().getWindow();
                        //        switchScene("WelcomePage.fxml", currentStage);
                        //    }
                        //src/main/resources/tn/esprit/medlist/WelcomePage.fxml
                        // TO LINK THE MAIN FORM
                        /*login_btn.getScene().getWindow().hide();
                        URL url = new File("src/main/resources/tn/esprit/medlist/WelcomePage.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        root.setOnMousePressed((MouseEvent event) ->{
                            x = event.getSceneX();
                            y = event.getSceneY();
                        });

                        root.setOnMouseDragged((MouseEvent event) ->{
                            stage.setX(event.getScreenX() - x);
                            stage.setY(event.getScreenY() - y);
                        });

                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setScene(scene);
                        stage.show();
*/
                    } else {
                        // ELSE, THEN ERROR MESSAGE WILL APPEAR
                        alert.errorMessage("Nom d'utilisateur ou mot de passe incorrectes");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void close(){
        System.exit(0);
    }

}
