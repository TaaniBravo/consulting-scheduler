package edu.wgu.tmaama;

import edu.wgu.tmaama.controllers.fxml.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Scheduler extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Scheduler.class.getResource("/views/Login.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 1000);
        stage.setScene(scene);
        LoginController loginController = loader.getController();
        loginController.setPrimaryStage(stage);
        stage.show();
    }

    public static void boot() {
        launch();
    }
}
