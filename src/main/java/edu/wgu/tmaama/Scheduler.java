package edu.wgu.tmaama;

import edu.wgu.tmaama.controllers.fxml.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Scheduler extends Application {
  public static void boot() {
    launch();
  }

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(Scheduler.class.getResource("/views/Login.fxml"));
    loader.setResources(ResourceBundle.getBundle("/bundles/translate"));
    System.out.println(Locale.getDefault().getLanguage());
    Scene scene = new Scene(loader.load(), 500, 500);
    stage.setScene(scene);
    LoginController loginController = loader.getController();
    loginController.setPrimaryStage(stage);
    stage.show();
  }
}
