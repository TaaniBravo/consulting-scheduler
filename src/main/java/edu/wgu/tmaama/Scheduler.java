package edu.wgu.tmaama;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * JavaFX application class. Called from Main to remove jar issues.
 */
public class Scheduler extends Application {
    public static void boot() {
        launch();
    }

    /**
     * Starts the application
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Scheduler.class.getResource("/views/Login.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles/main"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Client Scheduler");
        stage.show();
    }
}
