package edu.wgu.tmaama.controllers.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
    Stage primaryStage;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;

    public void initialize() {
        // TODO: init controller.
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
    }

    @FXML
    private void handleSignUp(ActionEvent actionEvent) {
    }
}
