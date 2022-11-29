package edu.wgu.tmaama;

import edu.wgu.tmaama.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Scheduler extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Scheduler.class.getResource("/views/Main.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 1000);
        stage.setScene(scene);
        MainController mainController = loader.getController();
        mainController.setPrimaryStage(stage);
        stage.show();
    }

    public static void boot() {
        launch();
    }
}
